FROM amazoncorretto:17-alpine-jdk

# Instalar dependências necessárias
RUN apk add --no-cache bash curl

# Criar um diretório para o app
WORKDIR /app

# Copiar tudo para o diretório do app
COPY . .

# Garantir permissões de execução para o Gradle Wrapper
RUN chmod +x gradlew

# Construir o app
RUN ./gradlew clean build -x test

# Expor a porta 8080
EXPOSE 8080

# Executar o app
CMD ["java", "-jar", "./build/libs/demo-0.0.1-SNAPSHOT.jar"]