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
echo "" > .env
echo "$SECRETS_JSON" | jq -r 'keys[] as $k | "\($k)=\(.[$k])"' >> .env

docker compose up -d