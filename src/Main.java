import com.google.gson.Gson;
import com.google.gson.JsonObject;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Challenge Conversor de Monedas");
        System.out.println("\n*****************BIENVENIDO AL SISTEMA CONVERSOR DE MONEDAS***************");

// Comando para leer datos ingresados por el usuario

        Scanner scanner = new Scanner(System.in);
        String direccion = "";
        int opcionMoneda = -1;
// Llave de ExchangeRate-API
        var llave = "4aefe2c702a94bd7ffe73c92";

        while (opcionMoneda != 7) {
            try {
// Opciones de Tipo de Cambio
                System.out.println("\n***************************************MENÚ*********************************");
                System.out.println("\n1) Dólar => Peso Argentino");
                System.out.println("2) Peso Argentino => Dólar");
                System.out.println("3) Dólar => Real Brasilero");
                System.out.println("4) Real Brasilero => Dólar");
                System.out.println("5) Dólar => Peso Colombiano");
                System.out.println("6) Peso Colombiano => Dólar");
                System.out.println("7) Salir");
                System.out.println("****************************************************************************");
                System.out.println("Por favor ingresa una de las opciones:");
                opcionMoneda = scanner.nextInt();

                if (opcionMoneda == 7) {
                    System.out.println("Hasta la próxima :)");
                    break;
                }

                System.out.println("Ingresa el valor que desea convertir");
                double montoAConvertir = scanner.nextDouble();


// Construye la URL que se utilizará para llamar a la API
                if (opcionMoneda == 1) {
                    direccion = "https://v6.exchangerate-api.com/v6/" + llave + "/pair/USD/ARS/" + montoAConvertir;
                } else if (opcionMoneda == 2) {
                    direccion = "https://v6.exchangerate-api.com/v6/" + llave + "/pair/ARS/USD/" + montoAConvertir;
                } else if (opcionMoneda == 3) {
                    direccion = "https://v6.exchangerate-api.com/v6/" + llave + "/pair/USD/BRL/" + montoAConvertir;
                } else if (opcionMoneda == 4) {
                    direccion = "https://v6.exchangerate-api.com/v6/" + llave + "/pair/BRL/USD/" + montoAConvertir;
                } else if (opcionMoneda == 5) {
                    direccion = "https://v6.exchangerate-api.com/v6/" + llave + "/pair/USD/COP/" + montoAConvertir;
                } else if (opcionMoneda == 6) {
                    direccion = "https://v6.exchangerate-api.com/v6/" + llave + "/pair/COP/USD/" + montoAConvertir;
                }
// Permite realizar solicitudes HTTP desde Java, crea la instancia para  enviar la solicitud a la API para traer la respuesta
// Objeto que representa la solicitud, construye la solicitud, indica a que URL que debe direccionar y finaliza la construcción del objeto
// Request envía la solicitud  usando el cliente y envía la solicitud de forma síncrona (espera hasta recibir la respuesta),  y por último le dice que la respuesta del cuerpo venga como una cadena de texto
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(direccion)).build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

// Imprime el resultado, solo para verificar donde devulve la API en un JSON
//          System.out.println(response.body());

// Aquí crea un objeto de la clase Gson que es una librería Google que permite convertir facilmente entre JSON y objetos Java
// contiene el texto JSON completo que devolvió la API
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

                String monedaBase = jsonObject.get("base_code").getAsString();
                String monedaConvertir = jsonObject.get("target_code").getAsString();
                double montoConversionRate = jsonObject.get("conversion_result").getAsDouble();
                System.out.println("****************************************************************************");
                System.out.println("La conversión de " + monedaBase + " a " + monedaConvertir + " es de " + montoConversionRate);
                System.out.println("****************************************************************************");
//            System.out.println(montoConversionRate);
            } catch (Exception e) {
                System.out.println("Ocurrió un error, por favor revisalo");
            }
        }
    }
}