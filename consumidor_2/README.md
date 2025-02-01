# DCNI_SUM_RABBITMQ - CONSUMIDOR 2
DESARROLLO CLOUD NATIVE I - SUMATIVA

# Iniciar Springboot
mvn spring-boot:run

# Limpia y recompila el proyecto
mvn clean install

# Levantar contenedor Docker
docker build -t amed_consumidor2 .
docker run --name amed_consumidor2 -p 8085:8085 amed_consumidor2

# DockerHub
1. Crear repo en https://hub.docker.com/
2. Primero, asegúrate de estar logueado en Docker Hub desde tu terminal
    docker login
3. Identifica tu imagen local. Puedes ver tus imágenes locales con:
    docker images
4. Etiqueta tu imagen local con el formato requerido por Docker Hub:
    Por ejemplo, si tu imagen local se llama "backend-app:1.0", los comandos serían:
    docker tag amed_consumidor2 espanderlof/dcni_sum_rabbitmq_consumidor2:latest
    docker push espanderlof/dcni_sum_rabbitmq_consumidor2:latest