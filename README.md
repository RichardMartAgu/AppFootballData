# Actividad de Aprendizaje 2a Evaluación - Programación Reactiva

### Este repositorio contiene el código fuente de la Actividad de Aprendizaje 2a Evaluación del curso 2023-2024 de Programación de Servicios y Procesos.

## Descripción del Proyecto
En esta actividad, desarrollamos una aplicación que utiliza técnicas de programación reactiva para consumir al menos una API y presenta la información al usuario en un interfaz gráfico desarrollado con JavaFX. La librería Retrofit se empleará para el consumo de la API.

## API Seleccionada
- La api seleccionada será la API : https://www.football-data.org/ 

## Funcionalidades
- En la actividad haremos dos llamada a la API donde mapearemos primero sus atributos y después la mostraremos por una interfaz en java FX.

- La primera llamada nos mostrará, mediante una llamada "GET" todos los equipos por orden en la clasificación actual de su competeción, eligiendo antes esa dicha competeción de entre una lista que se muestra en la interfaz gráfica.En esta lista aparece como gestionar una imagen dentro de un tableView.
  
- En la segunda llamada se mostrará , mediante otra llamada difernte "GET" una lista de la plantilla de un equipo seleccionado anteriormente de otra lista y lo mostrarán diferentes atributos en otra tableView .
  
- La interfaz tendrá un pulsador para cada llamada que hará la la misma en segundo plano, de manera concurrente, mostrando con un indicador de progreso sin fin, para indicar que esta llegando información.
  
- La reactividad se simulará con un "SLEEP" ya que la api no es una api reactiva, aunque si utilizarmos técnicas de programación reactiva.

- Esta API tiene un modelo de seguridad basado en api-Key añadido en el header. Gestionaremos esa seguridad con un interceptor para poder añadir esa key.

## Instrucciones de Ejecución

1. Clona este repositorio en tu máquina local.
2. Abre el proyecto en tu IDE preferido.
3. Configura las dependencias y la configuración de la API.
4. Ejecuta la aplicación en consola con "mvn javafx:run"








