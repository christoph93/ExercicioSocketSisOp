
import java.io.*;
import java.net.*;

class TCPServer {

    public static void main(String argv[]) throws Exception {
        String clientSentence;
        String echo;

        /* Cria socket do servidor */
        ServerSocket welcomeSocket = new ServerSocket(6790);
        
        FileReader fr;
        BufferedReader br;

        while (true) {

            /* Aguarda o recebimento de uma conexão. O servidor fica aguardando neste ponto 
			 * até que nova conexão seja aceita. */
            Socket connectionSocket = welcomeSocket.accept();

            /* Cria uma stream de entrada para receber os dados do cliente */
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            /* Cria uma stream de saída para enviar dados para o cliente */
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            /* Aguarda o envio de uma mensagem do cliente. Esta mensagem deve ser terminada em \n ou \r 
			 * Neste exemplo espera-se que a mensagem seja textual (string). Para ler dados não textuais tente
			 * a chamada read() */
            clientSentence = inFromClient.readLine();

            /* Determina o IP e Porta de origem */
            InetAddress IPAddress = connectionSocket.getInetAddress();
            int port = connectionSocket.getPort();

            /* Exibe, IP:port => msg */
            System.out.println(IPAddress.getHostAddress() + ":" + port + " => " + clientSentence);

            /* Adiciona o \n para que o cliente também possa ler usando readLine() */
            
            File f = new File(clientSentence);
            
            if(!f.exists()){
                connectionSocket.close();
            } else{
                       
            fr = new FileReader(clientSentence);
            br = new BufferedReader(fr);
            
            String  line = br.readLine();
            
            while(line != null){
                System.out.println(line);
                outToClient.writeBytes(line + "\n");
                line = br.readLine();
                
            }
            
            connectionSocket.close();
            echo = clientSentence + '\n';

            /* Envia mensagem para o cliente*/
            //outToClient.writeBytes(echo);

            /* Encerra socket do cliente */
            }
        }
    }
}
