FROM node:latest as reactbuild

COPY . ./

RUN ["npm", "install"]

RUN ["npm", "run", "build"]

FROM nginx:latest as hostnginx

WORKDIR /build
#Copy static files from the build folder from the first stage
COPY --from=reactbuild ./build /build

# Overwriting the /etc/nginx/nginx.conf with the existing nginx.conf

COPY ./nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
