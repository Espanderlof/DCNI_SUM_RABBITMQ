# DCNI_SUM_RABBITMQ - PRODUCTOR 2
DESARROLLO CLOUD NATIVE I - SUMATIVA

# Iniciar Springboot
mvn spring-boot:run

# Limpia y recompila el proyecto
mvn clean install

# Levantar contenedor Docker
docker build -t amed_productor2 .
docker run --name amed_productor2 -p 8084:8084 amed_productor2