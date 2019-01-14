namespace ElGamal
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.decrypt_button = new System.Windows.Forms.Button();
            this.encrypt_text_button = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.plainText = new System.Windows.Forms.TextBox();
            this.beta = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.alfa = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // decrypt_button
            // 
            this.decrypt_button.Location = new System.Drawing.Point(257, 197);
            this.decrypt_button.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.decrypt_button.Name = "decrypt_button";
            this.decrypt_button.Size = new System.Drawing.Size(84, 88);
            this.decrypt_button.TabIndex = 1;
            this.decrypt_button.Text = "Decrypt";
            this.decrypt_button.UseVisualStyleBackColor = true;
            this.decrypt_button.Click += new System.EventHandler(this.decrypt_button_Click);
            // 
            // encrypt_text_button
            // 
            this.encrypt_text_button.Location = new System.Drawing.Point(257, 67);
            this.encrypt_text_button.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.encrypt_text_button.Name = "encrypt_text_button";
            this.encrypt_text_button.Size = new System.Drawing.Size(84, 88);
            this.encrypt_text_button.TabIndex = 2;
            this.encrypt_text_button.Text = "Encrypt";
            this.encrypt_text_button.UseVisualStyleBackColor = true;
            this.encrypt_text_button.Click += new System.EventHandler(this.encrypt_text_button_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.8F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(458, 286);
            this.label1.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(132, 18);
            this.label1.TabIndex = 3;
            this.label1.Text = "Laboratory three";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(25, 41);
            this.label2.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(54, 13);
            this.label2.TabIndex = 4;
            this.label2.Text = "Plain Text";
            // 
            // plainText
            // 
            this.plainText.Location = new System.Drawing.Point(27, 67);
            this.plainText.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.plainText.Multiline = true;
            this.plainText.Name = "plainText";
            this.plainText.Size = new System.Drawing.Size(208, 88);
            this.plainText.TabIndex = 6;
            // 
            // beta
            // 
            this.beta.Location = new System.Drawing.Point(135, 219);
            this.beta.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.beta.Name = "beta";
            this.beta.Size = new System.Drawing.Size(100, 20);
            this.beta.TabIndex = 7;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.8F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.Location = new System.Drawing.Point(458, 314);
            this.label5.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(72, 18);
            this.label5.TabIndex = 10;
            this.label5.Text = "ElGamal";
            // 
            // alfa
            // 
            this.alfa.Location = new System.Drawing.Point(135, 197);
            this.alfa.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.alfa.Name = "alfa";
            this.alfa.Size = new System.Drawing.Size(100, 20);
            this.alfa.TabIndex = 11;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(98, 200);
            this.label3.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(24, 13);
            this.label3.TabIndex = 12;
            this.label3.Text = "alfa";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(100, 223);
            this.label4.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(28, 13);
            this.label4.TabIndex = 13;
            this.label4.Text = "beta";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(600, 366);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.alfa);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.beta);
            this.Controls.Add(this.plainText);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.encrypt_text_button);
            this.Controls.Add(this.decrypt_button);
            this.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.Name = "Form1";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Button decrypt_button;
        private System.Windows.Forms.Button encrypt_text_button;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox plainText;
        private System.Windows.Forms.TextBox beta;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox alfa;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
    }
}

