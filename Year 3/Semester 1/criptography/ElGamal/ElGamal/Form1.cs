using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Numerics;
using System.Security.Cryptography;

namespace ElGamal
{
    public partial class Form1 : Form
    {
        /* 
        * Alphabet:
        *  _ A B C D E F G H I J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z
        *  0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
        */

        BigInteger p, g, a;
        const int MAX_PRIME = 20000;
        const int ALPHABET_SIZE = 27;

        int kP = 2; // size of a plaintext chunk
        int lC = 3; // size of a plaintext chunk

        public void GenerateKeys()
        {
            // 1. Key generation. Alice creates a public key and a private key

            // 1.1. Generates a large random prime p and a generator g of (Zp *, .)
            p = new BigInteger(generateRandomPrime(MAX_PRIME));

            // 1.2. Selects a random integer a (1 ≤ a ≤ p − 2).
            a = generateRandomBigInteger(1, p - 2);

            // 1.3. Computes g^a mod p
            g = new BigInteger(bruteForceGenerator((int)p));

            MessageBox.Show("p = " + p + ", a = " + a + ", g = " + g);
            // 1.4. Alice’s public key is (p, g, g^a); her private key is a

        }

        public Form1()
        {
            InitializeComponent();

            // 1. Key generation
            GenerateKeys();
        }

        public BigInteger generateRandomBigInteger(BigInteger left, BigInteger right)
        {
            BigInteger a;
            RandomNumberGenerator rng = RandomNumberGenerator.Create();
            byte[] bytes = new byte[BigInteger.Divide(right, 2).ToByteArray().LongLength];
            do
            {
                rng.GetBytes(bytes);
                a = new BigInteger(bytes);
            } while (a < left || a >= right - 2);

            return a;

        }

        private int RandomFromList(List<int> lst)
        {
            Random rnd = new Random();
            return lst[rnd.Next(0, lst.Count)];
        }

        private int bruteForceGenerator(int p)
        {
            var r = from g in Enumerable.Range(2, p - 2).AsParallel()
                    where Enumerable.Range(1, p - 3).All(i => BigInteger.ModPow(g, i, p) != 1)
                    select g;

            return RandomFromList(r.ToList());
        }

        public int generateRandomPrime(int maxLimit)
        {
            int lowBound = (int)Math.Pow(27, kP);
            int highBound = (int)Math.Pow(27, lC);

            var r = from i in Enumerable.Range(lowBound, highBound - 1).AsParallel()
                    where Enumerable.Range(2, (int)Math.Sqrt(i)).All(j => i % j != 0)
                    select i;

            return RandomFromList(r.ToList());
    }

        public Tuple<BigInteger, string> encryptMessage(String s)
        {
            // 2. Encryption. Bob sends an encrypted message to Alice.
            BigInteger k, alpha, beta;
            string betaAsString = "";

            int pos;
            char c;

            // 2.1 Gets Alice’s public key (p, g, g^a).
            // 2.3. Selects a random integer k (1 ≤ k ≤ p − 2).
            k = generateRandomBigInteger(1, p - 2);

            // 2.4. Computes alpha = g^k mod p and β = m * ((g^a)^k) mod
            alpha = BigInteger.ModPow(g, k, p);

            // 2.2. Represents the message as a number m between 0 and p − 1.
            // Plaintext - chunks of 2 letters
            // Cipertext - chunks of 3 letters
            // -------------------------------------------------------------------------------------------------------

            for (int i = 0; i < s.Length; i += 2)
            {
                int sumChunks = 0;
                for (int j = 0; j < kP; j++)
                {
                    if (i + j >= s.Length)
                    {
                        c = ' ';
                    }
                    else
                    {
                        c = s[i + j];
                    }

                    pos = (c == ' ') ? (0) : (c - 'a' + 1);
                    sumChunks += pos * (int)Math.Pow(ALPHABET_SIZE, kP - j - 1);
                }

                beta = BigInteger.ModPow(BigInteger.Multiply(sumChunks, BigInteger.ModPow(BigInteger.ModPow(g, a, p), k, p)), 1, p);
                int coeff = 0;
                string outputAsText = "";
                char cryptedChar;

                int betaAsInt = (int)beta;
                for (int z = 0; z < lC; z++)
                {
                    coeff = betaAsInt % ALPHABET_SIZE;
                    cryptedChar = (coeff == 0) ? ((char)(' ')) : ((char)('a' + coeff - 1));
                    betaAsInt = betaAsInt / ALPHABET_SIZE;

                    outputAsText = cryptedChar + outputAsText;
                }
                betaAsString += outputAsText;
            }
            // -------------------------------------------------------------------------------------------------------

            // 2.5 Sends the ciphertext c = (alhpa, Beta) to Alice
            return new Tuple<BigInteger, string>(alpha, betaAsString);
        }

        public string decryptMessage(BigInteger alpha, string betaText)
        {
            BigInteger exp,t1 = 1, t2;
            int pos;
            char c;
            string decryptedText = "";

            //exp = BigInteger.Subtract(BigInteger.Subtract(p, 1), a);
            //while (exp != 0)
            //{
            //    t1 = BigInteger.Multiply(t1, alpha);
            //    exp = BigInteger.Subtract(exp, 1);
            //}

            BigInteger lhs = BigInteger.ModPow(alpha, p - 1 - a, p);
            BigInteger res;
            for (int i = 0; i < betaText.Length; i += 3)
            {
                int sumChunks = 0;
                for (int j = 0; j < lC; j++)
                {
                    if (i + j >= betaText.Length)
                    {
                        c = ' ';
                    }
                    else
                    {
                        c = betaText[i + j];
                    }

                    pos = (c == ' ') ? (0) : (c - 'a' + 1);
                    sumChunks += pos * (int)Math.Pow(ALPHABET_SIZE, lC - j - 1);

                }

                res = BigInteger.Multiply(lhs, sumChunks);
                res = BigInteger.ModPow(res, 1, p);

                int coeff = 0;
                string outputAsText = "";
                char cryptedChar;

                int betaAsInt = (int)res;
                for (int z = 0; z < kP; z++)
                {
                    coeff = betaAsInt % ALPHABET_SIZE;
                    cryptedChar = (coeff == 0) ? ((char)(' ')) : ((char)('a' + coeff - 1));
                    betaAsInt = betaAsInt / ALPHABET_SIZE;

                    outputAsText = cryptedChar + outputAsText;
                }
                decryptedText += outputAsText;
            }

            return decryptedText;
        }

        public Boolean validateText(String s)
        {
            foreach (char c in s)
            {
                if (((c < 'a') || (c > 'z')) && (c != ' '))
                {
                    return false;
                }
            }
            return true;
        }

        public Boolean validateEncryptedText(String s)
        {
            foreach (char c in s)
            {
                if (((c < 'A') || (c > 'Z')) && (c != ' '))
                {
                    return false;
                }
            }
            return true;
        }

        public int posInAlphabet(char c)
        {
            int pos;

            if (c == ' ')
            {
                pos = 0;
            }
            else
            {
                if (c >= 'a' && c <= 'z')
                {
                    pos = (c - 'a') + 1;
                }
                else
                {
                    pos = (c - 'A') + 1;
                }
            }

            return pos;
        }


        private void encrypt_text_button_Click(object sender, EventArgs e)
        {
            String text = this.plainText.Text.ToString();
            if (this.validateText(text) == false)
            {
                MessageBox.Show("The plain text should be lower case");
                MessageBox.Show("Also it should only have letters");
            }
            else
            {

                String input = this.plainText.Text;
                MessageBox.Show("Input message: " + input);
                Tuple<BigInteger, string> encrypted = this.encryptMessage(input);
                this.alfa.Text = encrypted.Item1.ToString();
                this.beta.Text = encrypted.Item2.ToString();
            }
        }

        private void decrypt_button_Click(object sender, EventArgs e)
        {
            string res = this.decryptMessage(BigInteger.Parse(this.alfa.Text), this.beta.Text);
            this.plainText.Text = res.ToUpper().ToString();
        }
    }
}
