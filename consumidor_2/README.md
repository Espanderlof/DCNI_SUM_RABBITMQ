# DCNI_SUM_RABBITMQ - CONSUMIDOR 2
DESARROLLO CLOUD NATIVE I - SUMATIVA

# Iniciar Springboot
mvn spring-boot:run

# Limpia y recompila el proyecto
mvn clean install

# Levantar contenedor Docker
docker build -t amed_consumidor2 .
docker run --name amed_consumidor2 -p 8085:8085 amed_consumidor2