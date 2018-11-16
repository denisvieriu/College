using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace lab5_2
{
    public class StateObject
    {
        // Client socket.  
        public Socket workSocket = null;
        // Size of receive buffer.  
        public const int BufferSize = 256;
        // Receive buffer.  
        public byte[] buffer = new byte[BufferSize];
        // Received data string.  
        public StringBuilder sb = new StringBuilder();
    }

    public class AsynchronousClient
    {
        private const string CONTENT_LENGTH = "Content-Length";
        // The port number for the remote device.  
        private const int port = 80;

        // ManualResetEvent instances signal completion.  
        private static ManualResetEvent connectDone =
            new ManualResetEvent(false);
        private static ManualResetEvent sendDone =
            new ManualResetEvent(false);
        private static ManualResetEvent receiveDone =
            new ManualResetEvent(false);

        private static StringBuilder gSb = new StringBuilder();

        // The response from the remote device.  
        private static String response = String.Empty;

        private static String host = "cs.ubbcluj.ro";

        private static void WaitAsync()
        {
            Task t = StartClientAsync();
            t.Wait();
        }

        private static async Task StartClientAsync()
        {
            // Connect to a remote device.  
            try
            {
                // reference: https://stackoverflow.com/questions/25814575/asynchronous-socket-operations-in-a-task
                // https://codereview.stackexchange.com/questions/177526/c-async-socket-wrapper

                // Establish the remote endpoint for the socket.  
                IPHostEntry ipHostInfo = Dns.GetHostEntry(host);
                IPAddress ipAddress = ipHostInfo.AddressList[0];
                IPEndPoint remoteEP = new IPEndPoint(ipAddress, port);

                // Create a TCP/IP socket.  
                Socket client = new Socket(ipAddress.AddressFamily,
                    SocketType.Stream, ProtocolType.Tcp);

                // Connect to the remote endpoint.  
                await Task.Factory.FromAsync(client.BeginConnect, client.EndConnect, host, port, null);
                //client.BeginConnect(remoteEP,
                //    new AsyncCallback(ConnectCallback), client);

                //// Waits untill ConnectCallback is called
                //connectDone.WaitOne();
                Console.WriteLine("Socket connected");


                // Send test data to the remote device.  

                Console.WriteLine("H1");
                await Send(client, "GET / HTTP/1.1\r\nHost: " + host + "\r\nConnection: close\r\n\r\n");
                
                Console.WriteLine("H2");


                //Send(client, "GET / HTTP/1.1\r\nHost: " + host + "\r\nUser - Agent: Mozilla / 5.0(Windows NT 10.0; Win64; x64; rv: 56.0) Gecko / 20100101 Firefox / 56.0\r\nAccept: text / html,application / xhtml + xml,application / xml; q = 0.9,*/*;q=0.8\r\nAccept - Language: en-US,en;q=0.5\r\nAccept - Encoding: gzip, deflate\r\nReferer: http://clipart-library.com/clipart/575061.htm\r\nConnection: keep-alive\r\nUpgrade - Insecure-Requests: 1\r\nIf - Modified-Since: Wed, 01 Mar 2017 15:35:52 GMT\r\nIf - None-Match: \"58b6ea58-12969\"\r\nCache - Control: max-age=0\r\n\r\n");
                //Send(client, "GET www.google.com HTTP/1.1");

                // Receive the response from the remote device.  
                await Receive(client);

                // Write the response to the console.  
                Console.WriteLine("Response received : {0}", response);

                // Release the socket.  
                client.Shutdown(SocketShutdown.Both);
                client.Close();

            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        private static void ConnectCallback(IAsyncResult ar)
        {
            try
            {
                // Retrieve the socket from the state object.  
                Socket client = (Socket)ar.AsyncState;

                // Complete the connection.  
                client.EndConnect(ar);

                Console.WriteLine("Socket connected to {0}",
                    client.RemoteEndPoint.ToString());

                // Signal that the connection has been made.  
                connectDone.Set();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        private static async Task Receive(Socket client)
        {
            try
            {
                // Create the state object.  
                StateObject state = new StateObject
                {
                    workSocket = client
                };

                await ReceiveAsync(client, state.buffer, 0, StateObject.BufferSize);
                // Begin receiving the data from the remote device.  
                //client.BeginReceive(state.buffer, 0, StateObject.BufferSize, 0,
                //    new AsyncCallback(ReceiveCallback), state);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        private static async Task<int> ReceiveAsync(Socket _socket, byte[] buffer, int offset, int size)
        {
            int bytesReceived;
            try
            {
                while (true)
                {
                    bytesReceived = await Task<int>.Factory.FromAsync(
                        _socket.BeginReceive(buffer, offset, size, SocketFlags.None, null, null),
                        _socket.EndReceive);

                    //response = Encoding.ASCII.GetString(buffer, 0, bytesReceived);

                    gSb.Append(Encoding.ASCII.GetString(buffer, 0, bytesReceived));

                    //
                    // headersAndBody[0] will contain the headers
                    // headersAndBody[1] will contain the body
                    //
                    string[] separator = new string[] { "\r\n\r\n" };
                    string[] headersAndBody = gSb.ToString().Split(separator, System.StringSplitOptions.RemoveEmptyEntries);

                    if (headersAndBody[1].Length < GetContentLength(gSb.ToString()))
                    {
                        // There might be more data, so store the data received so far.  

                        // Get the rest of the data.  
                        Array.Clear(buffer, 0, buffer.Length);
                        continue;
                    }
                    else
                    {
                        // All the data has arrived; put it in response.  
                        if (gSb.Length > 1)
                        {
                            response = gSb.ToString();
                        }
                        break;
                    }
                }
            }
            catch (Exception e)
            {
                if (e is SocketException se && se.SocketErrorCode == SocketError.ConnectionReset)
                {
                    throw new Exception("Connection reset");
                }

                throw new Exception("Failed to receieve message", e);
            }

            if (bytesReceived == 0)
            {
                throw new Exception("Connection closed");
            }


            return bytesReceived;
        }

        //private static async Task<int> ReceiveAsync(Socket _socket, byte[] buffer, int offset, int count)
        //{
        //    using (var stream = new NetworkStream(_socket))
        //    {
        //        return await stream.ReadAsync(buffer, offset, count);
        //    }
        //}

        private static int GetContentLength(string retrievedData)
        {
            string[] delimiter = new string[] { "\r\n" };
            string[] dataSplitAfterLine = retrievedData.Split(delimiter, System.StringSplitOptions.None);

            foreach (string line in dataSplitAfterLine)
            {
                string[] crtHeader = line.Split(':');
                if (crtHeader[0].Equals(CONTENT_LENGTH)) { return int.Parse(crtHeader[1]); }
            }

            return 0;
        }


        private static void ReceiveCallback(IAsyncResult ar)
        {
            try
            {
                // Retrieve the state object and the client socket   
                // from the asynchronous state object.  
                StateObject state = (StateObject)ar.AsyncState;
                Socket client = state.workSocket;

                // Read data from the remote device.  
                int bytesRead = client.EndReceive(ar);
                state.sb.Append(Encoding.ASCII.GetString(state.buffer, 0, bytesRead));

                //
                // headersAndBody[0] will contain the headers
                // headersAndBody[1] will contain the body
                //
                string[] separator = new string[] { "\r\n\r\n" };
                string[] headersAndBody = state.sb.ToString().Split(separator, System.StringSplitOptions.RemoveEmptyEntries);

                if (headersAndBody[1].Length < GetContentLength(state.sb.ToString()))
                {
                    // There might be more data, so store the data received so far.  

                    // Get the rest of the data.  
                    client.BeginReceive(state.buffer, 0, StateObject.BufferSize, 0,
                        new AsyncCallback(ReceiveCallback), state);
                }
                else
                {
                    // All the data has arrived; put it in response.  
                    if (state.sb.Length > 1)
                    {
                        response = state.sb.ToString();
                    }
                    // Signal that all bytes have been received.  
                    receiveDone.Set();
                }


                //Console.WriteLine("Bytes read {0}\n", state.sb.Length);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        private static async Task Send(Socket client, String data)
        {
            // Convert the string data to byte data using ASCII encoding.  
            //byte[] byteData = Encoding.ASCII.GetBytes(data);
            ////wrGETURL.ContentLength = bytes.Length;


            //// Begin sending the data to the remote device.  
            //client.BeginSend(byteData, 0, byteData.Length, 0,
            //    new AsyncCallback(SendCallback), client);

            await SendAsync(client, data, 0, data.Length);
        }

        private static async Task<int> SendAsync(Socket _socket, String data, int offset, int count)
        {
            byte[] buffer = Encoding.ASCII.GetBytes(data);
            try
            {
                return await Task<int>.Factory.FromAsync(
                    _socket.BeginSend(buffer, offset, count, SocketFlags.None, null, null),
                    _socket.EndSend);
            }
            catch (Exception e)
            {
                throw new Exception("Failed to send message", e);
            }
        }

        private static void SendCallback(IAsyncResult ar)
        {
            try
            {
                // Retrieve the socket from the state object.  
                Socket client = (Socket)ar.AsyncState;

                // Complete sending the data to the remote device.  
                int bytesSent = client.EndSend(ar);
                Console.WriteLine("Sent {0} bytes to server.", bytesSent);

                // Signal that all bytes have been sent.  
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        private static Task IsDataSet()
        {
            return Task.FromResult(0);
        }

        public static int Main(String[] args)
        {
            WaitAsync();
            return 0;
        }

    }
}
