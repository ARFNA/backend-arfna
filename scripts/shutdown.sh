echo "About to shut down!"
sudo kill $(lsof -t -i:8080)