SWARM_HOST=10.1.0.20

for i in {1..10}
do
    curl -s ${SWARM_HOST}:8080
    echo -e "\n\n"
done