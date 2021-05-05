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
      "lat": 55.568,
      "lon": 37.8488,
      "address": "Площадь Ленина"
    },
    "description": "string",
    "conflict": true,
    "messages": 0
  },
    {
    "id": "2",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "BREAK",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "UNKNOWN",
    "creator": "string",
    "location": {
      "lat": 45.05739,
      "lon": 38.96582333,
      "address": "Краснодарск"
    },
    "description": "Могло быть и хуже",
    "conflict": true,
    "messages": 0
  },
    {
    "id": "607b28f563d52731fca2798b",
    "created": "2021-04-17T18:29:09.000+00:00",
    "type": "STEAL",
    "verified": false,
    "hidden": false,
    "hardness": "UNKNOWN",
    "creator": "607b289b63d52731fca27989",
    "location": {
      "lat": 55.2028,
      "lon": 37.3100001,
      "address": "string"
    },
    "description": "кто-то что-то как-то",
    "conflict": false,
    "messages": 0
  },
  {
    "id": "3",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "MOTO_MOTO",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "UNKNOWN",
    "creator": "string",
    "location": {
      "lat": 55.57,
      "lon": 37.3580001,
      "address": "string"
    },
    "description": "string",
    "conflict": true,
    "messages": 0
  },
    {
    "id": "4",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "MOTO_AUTO",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "NO",
    "creator": "string",
    "location": {
      "lat": 55.584028,
      "lon": 37.38280001,
      "address": "string"
    },
    "description": "string",
    "conflict": true,
    "messages": 0
  },
  {
    "id": "5607b28f563d52731fca2798b",
    "created": "2021-04-17T18:29:09.000+00:00",
    "type": "MOTO_PEDESTRIAN",
    "verified": false,
    "hidden": false,
    "hardness": "LIGHT",
    "creator": "607b289b63d52731fca27989",
    "location": {
      "lat": 45.06739,
      "lon": 38.93582333,
      "address": "Краснодарск"
    },
    "description": "Упал, очнулся - гипс",
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
    "hardness": "HEAVY",
    "creator": "string",
    "location": {
      "lat": 55.65028,
      "lon": 37.4582201,
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
    "hardness": "LETHAL",
    "creator": "string",
    "location": {
      "lat": 55.665128,
      "lon": 37.4592201,
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
      "lat": 55.75228,
      "lon": 37.4599201,
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
      "lat": 55.6228,
      "lon": 37.4609201,
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
      "lat": 55.7228,
      "lon": 37.4709201,
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
      "lat": 55.87,
      "lon": 37.4719201,
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
      "lat": 55.8,
      "lon": 37.64819201,
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
      "lat": 55.80,
      "lon": 37.75813201,
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
      "lat": 55.69,
      "lon": 37.5913201,
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
      "lat": 56.70,
      "lon": 37.6213201,
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
      "lat": 56.80,
      "lon": 37.6413201,
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
      "lat": 56.85,
      "lon": 37.6613201,
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
      "lat": 56.8,
      "lon": 37.8613201,
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
      "lat": 54.99,
      "lon": 37.9913201,
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
      "lat": 55.0,
      "lon": 37.8913901,
      "address": "Площадь Ленина"
    },
    "description": "кто-то что-то как-то",
    "conflict": false,
    "messages": 4
  }
  
]
"""
