# ApplicationRegie
Projet Tutoré IUT 2014-2015

##Format de données
###Localisation
####Localisation à régie
{
  "idModule": "localisation",
  "action": "init"
}

{
  "idModule": "localisation",
  "action": "calibrage",
  "statut": "NOK" || "OK",
  "message": "..." //Facultatif
}

{
  "idModule": "localisation",
  "robots": [
     {
      "id": 0,
      "x": 15,
      "y": 2
    },
    {
      "id": 1,
      "x": 3,
      "y": 37
    }
  ]
}

####Régie à localisation

{
  "action": "calibrage",
  "valeur": 25.3
}

{
  "action": "start"
}

{
  "action": "stop"
}


###ApplicationPublic
{
  "idModule": "public",
  "action": "init"
}

{
  "attente": true,
  "secondeAttente": 10
  "secondeControle": 10
}

{
  "idModule": "public",
  "detail": {
    "action": "avancer",
    "vitesse": 15
  }
}

{
  "idModule": "public",
  "detail": {
    "action": "fin"
  }
}

###Robot
{
  "idModule": "raspberryRobot",
  "action": "init"
}

{
  "action": "avancer",
  "vitesse": "15"
}
