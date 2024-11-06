# Utiliser une image de Node.js pour construire l'application
FROM node:14 AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers du projet
COPY package*.json ./
RUN npm install
COPY . .

# Construire l'application Angular
RUN npm run build --prod

# Étape pour servir l'application avec Nginx
FROM nginx:alpine
COPY --from=build /app/dist/tpfoyerangular /usr/share/nginx/html

# Exposer le port 80
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
