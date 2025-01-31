# DCNI_SUM_RABBITMQ - PRODUCTOR 1
DESARROLLO CLOUD NATIVE I - SUMATIVA

# Iniciar Springboot
mvn spring-boot:run

# Limpia y recompila el proyecto
mvn clean install

# Levantar contenedor Docker
docker build -t amed_productor1 .
docker run --name amed_productor1 -p 8082:8082 amed_productor1