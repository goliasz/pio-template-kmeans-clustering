# pio-template-kmeans-clustering

PredictionIO template clustering 2D points. 

## Docker Part

docker pull goliasz/docker-predictionio<br>
cd $HOME<br>
mkdir MyEngine<br>
docker run --hostname pio1 --privileged=true --name pio1 -it -p 8000:8000 -p 7070:7070 -p 7071:7071 -p 7072:7072 -v $HOME/MyEngine:/MyEngine goliasz/docker-predictionio /bin/bash<br>

## PIO Part

root@pio1:/# pio-start-all<br>
root@pio1:/# cd MyEngine<br>
root@pio1:/MyEngine# pio template get goliasz/pio-template-kmeans-clustering --version "0.3" geoclus<br>
root@pio1:/MyEngine/textsim# vi engine.json<br>
Set application name to “geoclus”<br>
<br>
root@pio1:/MyEngine/textsim# pio build --verbose<br>
root@pio1:/MyEngine/textsim# pio app new geoclus<br>
root@pio1:/MyEngine/textsim# sh ./data/import.sh [YOUR APP ID from "pio app new textsim" output]<br>
root@pio1:/MyEngine/textsim# pio train<br>
root@pio1:/MyEngine/textsim# pio deploy --port 8000<br>
