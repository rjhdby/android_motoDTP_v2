package motocitizen.data.network.factory.mocks

const val accident = """
{
  "id": "string",
  "created": "2021-04-15T10:32:36.556Z",
  "type": "OTHER",
  "resolved": "2021-04-15T10:32:36.556Z",
  "verified": true,
  "hidden": true,
  "hardness": "UNKNOWN",
  "creator": "string",
  "location": {
    "lat": 54.2,
    "lon": 37.1,
    "address": "Площадь Ленина"
  },
  "description": "string",
  "conflict": true,
  "messages": 0
}
"""

const val getAccidentList = """
[
   {
      "id":"2",
      "created":1617587675,
      "updated" : 1617612875,
      "verified":false,
      "hidden":false,
      "description":"Все хорошо",
      "conflict":false
   },
   {
      "id":"3",
      "created":1617674075,
      "updated" : 1617728075,
      "verified":true,
      "hidden":false,
      "description":"Упс",
      "conflict": true
   }
]
"""
