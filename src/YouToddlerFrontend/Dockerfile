FROM node:latest as BUILDSTAGE
EXPOSE 8888

COPY . /app
WORKDIR /app
RUN ["npm", "install"]
RUN ["npm", "run", "build"]

FROM nginx:latest
COPY --from=BUILDSTAGE /app/build/ /usr/share/nginx/html/
EXPOSE 80 443