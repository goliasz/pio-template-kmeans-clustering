# pio-template-kmeans-clustering

PredictionIO template clustering for 2D points. 

## Docker Part
```
docker pull goliasz/docker-predictionio
cd $HOME
mkdir MyEngine
docker run --hostname pio1 --name pio1 -it -v $HOME/MyEngine:/MyEngine goliasz/docker-predictionio /bin/bash
```
## PIO Part
```
root@pio1:/# pio-start-all
root@pio1:/# cd MyEngine
root@pio1:/MyEngine# pio template get goliasz/pio-template-kmeans-clustering --version "0.5" geoclus
root@pio1:/MyEngine# cd geoclus
root@pio1:/MyEngine/geoclus# vi engine.json
```
Set application name to “geoclus”

```
root@pio1:/MyEngine/geoclus# pio build --verbose
root@pio1:/MyEngine/geoclus# pio app new geoclus
root@pio1:/MyEngine/geoclus# sh ./data/import.sh [YOUR APP ID from "pio app new textsim" output]
root@pio1:/MyEngine/geoclus# pio train
root@pio1:/MyEngine/geoclus# pio deploy --port 8000 &
```
