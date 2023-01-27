FROM nginx:latest

# Agregar TOKEN y BUILDER_URL como variables de entorno
ENV BUILDER_URL=192.168.1.1
ENV JOB=YOUR_JOB_NAME
ENV BASICAUTH="USER:TOKEN"

# Crear site que atiende la petición
COPY src/site.conf /etc/nginx/conf.d/site.conf

# Crear usuario "testuser"
RUN adduser --system --disabled-login --ingroup nginx --no-create-home --home /nonexistent --gecos "nginx testuser" --shell /bin/false testuser

# Change the user and group of the nginx process to testuser
RUN sed -i "s/user\s*root;/user testuser;/g" /etc/nginx/nginx.conf

# Change the user and group of the nginx process to testuser and edit nginx conf to listen on port 8081
RUN sed -i "s/user\s*root;/user testuser;/g;s/listen\s*80;/listen 8081;/g" /etc/nginx/nginx.conf

# Hacer petición POST a BUILDER al iniciar el contenedor
RUN echo "curl -X GET http://localhost:8081/build" >> $HOME/.bashrc

EXPOSE 8081

CMD ["nginx", "-g", "daemon off;"]
# CMD ["/bin/bash", "-c", "sed -i -e 's|{{ basicauth }}|'\"$BASICAUTH\"'|' -e 's%{{ proxy }}%http://'\"$BUILDER_URL\"'/job/'\"$JOB\"'/build%' /etc/nginx/conf.d/site.conf && exec nginx -g 'daemon off;'"]
