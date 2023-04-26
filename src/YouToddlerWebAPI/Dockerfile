FROM eclipse-temurin:8-focal

# We assume the project is already compiled
COPY YouToddlerWebAPI/ /build/
WORKDIR /build/
RUN chmod a+rx mvnw
RUN ./mvnw clean package spring-boot:repackage
RUN cp YouToddlerWebAPI/target/*.jar /webapi/
COPY YouToddlerCLI/publish/* /webapi/

WORKDIR /webapi/
EXPOSE 9090

# Setting up things
RUN apt-get update \
    && DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
        curl \
        ffmpeg \
        python3 python3-pip \
        ca-certificates \
        \
        # .NET dependencies
        libc6 \
        libgcc1 \
        libgssapi-krb5-2 \
        libicu66 \
        libssl1.1 \
        libstdc++6 \
        zlib1g \
    && rm -rf /var/lib/apt/lists/*

RUN curl -sSL https://dot.net/v1/dotnet-install.sh | bash /dev/stdin -Channel 7.0 -Runtime dotnet -InstallDir /usr/share/dotnet \
    && ln -s /usr/share/dotnet/dotnet /usr/bin/dotnet

RUN curl -L https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp -o /usr/local/bin/yt-dlp
RUN chmod a+rx /usr/local/bin/yt-dlp