# ApplicationRegie
Projet Tutoré IUT 2014-2015

##Format de données
###Localisation
{
  "idModule": "localisation",
  "action": "init"
}

{
  "action": "calibrage",
  "valeur": 25.3
}

{
  "idModule": "localisation",
  "action": "calibrage",
  "statut": "NOK"
}

{
  "action": "start"
}

{
  "action": "stop"
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

###ApplicationPublic
{
  "idModule": "public",
  "detail": {
    "action": "connecte",
  }
}

{
  "attente": true,
  "secondeAttente": 10
}

{
  "idModule": "public",
  "detail": {
    "action": "avancer",
    "vitesse": "15"
  }
}

###Robot
{
  "idModule": "raspberryRobot",
  "etat": "pret"
}

{
  "action": "avancer",
  "vitesse": "15"
}