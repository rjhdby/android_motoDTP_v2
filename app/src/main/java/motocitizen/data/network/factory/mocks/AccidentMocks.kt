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
    "id": "1",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "UNKNOWN",
    "creator": "string",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "Площадь Ленина"
    },
    "description": "string",
    "conflict": true,
    "messages": 0
  },
    {
    "id": "2",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "UNKNOWN",
    "creator": "string",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "string"
    },
    "description": "string",
    "conflict": true,
    "messages": 0
  },
  {
    "id": "607b28f563d52731fca2798b",
    "created": "2021-04-17T18:29:09.000+00:00",
    "type": "OTHER",
    "verified": false,
    "hidden": false,
    "hardness": "UNKNOWN",
    "creator": "607b289b63d52731fca27989",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "string"
    },
    "description": "кто-то что-то как-то",
    "conflict": false,
    "messages": 0
  },
  {
    "id": "3",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "UNKNOWN",
    "creator": "string",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "string"
    },
    "description": "string",
    "conflict": true,
    "messages": 0
  },
    {
    "id": "4",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "UNKNOWN",
    "creator": "string",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "string"
    },
    "description": "string",
    "conflict": true,
    "messages": 0
  },
  {
    "id": "5607b28f563d52731fca2798b",
    "created": "2021-04-17T18:29:09.000+00:00",
    "type": "OTHER",
    "verified": false,
    "hidden": false,
    "hardness": "UNKNOWN",
    "creator": "607b289b63d52731fca27989",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "string"
    },
    "description": "кто-то что-то как-то",
    "conflict": false,
    "messages": 0
  },
  {
    "id": "6",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "UNKNOWN",
    "creator": "string",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "string"
    },
    "description": "string",
    "conflict": true,
    "messages": 0
  },
    {
    "id": "7",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "UNKNOWN",
    "creator": "string",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "string"
    },
    "description": "string",
    "conflict": true,
    "messages": 0
  },
  {
    "id": "8607b28f563d52731fca2798b",
    "created": "2021-04-17T18:29:09.000+00:00",
    "type": "OTHER",
    "verified": false,
    "hidden": false,
    "hardness": "UNKNOWN",
    "creator": "607b289b63d52731fca27989",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "string"
    },
    "description": "кто-то что-то как-то",
    "conflict": false,
    "messages": 0
  },
  {
    "id": "9",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "UNKNOWN",
    "creator": "string",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "string"
    },
    "description": "string",
    "conflict": true,
    "messages": 0
  },
    {
    "id": "10",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "UNKNOWN",
    "creator": "string",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "string"
    },
    "description": "string",
    "conflict": true,
    "messages": 0
  },
  {
    "id": "11607b28f563d52731fca2798b",
    "created": "2021-04-17T18:29:09.000+00:00",
    "type": "OTHER",
    "verified": false,
    "hidden": false,
    "hardness": "UNKNOWN",
    "creator": "607b289b63d52731fca27989",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "string"
    },
    "description": "кто-то что-то как-то",
    "conflict": false,
    "messages": 0
  }
  ,{
    "id": "12",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "UNKNOWN",
    "creator": "string",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "string"
    },
    "description": "string",
    "conflict": true,
    "messages": 1
  },
    {
    "id": "132",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "UNKNOWN",
    "creator": "string",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "string"
    },
    "description": "string",
    "conflict": true,
    "messages": 6
  },
  {
    "id": "14607b28f563d52731fca2798b",
    "created": "2021-04-17T18:29:09.000+00:00",
    "type": "OTHER",
    "verified": false,
    "hidden": false,
    "hardness": "UNKNOWN",
    "creator": "607b289b63d52731fca27989",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "string"
    },
    "description": "кто-то что-то как-то",
    "conflict": false,
    "messages": 8
  },{
    "id": "151",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "UNKNOWN",
    "creator": "string",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "string"
    },
    "description": "string",
    "conflict": true,
    "messages": 1
  },
    {
    "id": "162",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "UNKNOWN",
    "creator": "string",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "string"
    },
    "description": "string",
    "conflict": true,
    "messages": 0
  },
  {
    "id": "17607b28f563d52731fca2798b",
    "created": "2021-04-17T18:29:09.000+00:00",
    "type": "OTHER",
    "verified": false,
    "hidden": false,
    "hardness": "UNKNOWN",
    "creator": "607b289b63d52731fca27989",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "Где-то"
    },
    "description": "кто-то что-то как-то",
    "conflict": false,
    "messages": 8
  },{
    "id": "181",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "UNKNOWN",
    "creator": "string",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "Москва"
    },
    "description": "string",
    "conflict": true,
    "messages": 2
  },
    {
    "id": "192",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "UNKNOWN",
    "creator": "string",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "адресс"
    },
    "description": "string",
    "conflict": true,
    "messages": 4
  },
  {
    "id": "20607b28f563d52731fca2798b",
    "created": "2021-04-17T18:29:09.000+00:00",
    "type": "OTHER",
    "verified": false,
    "hidden": false,
    "hardness": "UNKNOWN",
    "creator": "607b289b63d52731fca27989",
    "location": {
      "lat": 0,
      "lon": 0,
      "address": "Площадь Ленина"
    },
    "description": "кто-то что-то как-то",
    "conflict": false,
    "messages": 4
  }
]
"""
