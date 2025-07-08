#!/bin/bash

# Update system and install Docker
sudo dnf update -y
sudo dnf install docker -y
sudo systemctl start docker
sudo usermod -aG docker $USER

# Install Docker Compose
mkdir -p /usr/local/lib/docker/cli-plugins
curl -SL https://github.com/docker/compose/releases/latest/download/docker-compose-linux-x86_64 \
  -o /usr/local/lib/docker/cli-plugins/docker-compose
chmod +x /usr/local/lib/docker/cli-plugins/docker-compose

# Clone the repository and start services
sudo dnf install git -y
cd /home/ec2-user
git clone ${repository_url}
cd ${project_name}

# Get secrets from AWS
SECRETS_JSON=$(aws secretsmanager get-secret-value --secret-id prd/authentication-backend/secrets --query SecretString --output text --region us-east-1)
cat <<EOF > .env
POSTGRES_DB_USER=$(echo $SECRETS_JSON | jq -r '.POSTGRES_DB_USER')
POSTGRES_DB_PASSWORD=$(echo $SECRETS_JSON | jq -r '.POSTGRES_DB_PASSWORD')
SMTP_PASSWORD=$(echo $SECRETS_JSON | jq -r '.SMTP_PASSWORD')
JWT_SECRET=$(echo $SECRETS_JSON | jq -r '.JWT_SECRET')
EOF

docker compose up -d