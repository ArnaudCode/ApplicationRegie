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
  "statut": "OK"     OU    "statut": "NOK"
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
    [
      15,
      27
    ],
    [
      3,
      16
    ]
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
