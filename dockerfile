# Usa a imagem oficial do JDK 17 como base
FROM openjdk:11-jdk-slim

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o JAR gerado pelo Maven para dentro do container
COPY target/*.jar app.jar

# Expõe a porta da aplicação
EXPOSE 8080

# Define o comando para rodar a aplicação
CMD ["java", "-jar", "app.jar"]