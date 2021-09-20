package motocitizen.data.network.factory.mocks

const val newAccident = """
{
  "id": "1",
  "created": "2021-04-15T10:32:36.000+00:00",
  "type": "OTHER",
  "resolved": "2021-04-15T10:32:36.000+00:00",
  "verified": true,
  "hidden": true,
  "creator": "string",
  "creatorNick": "123",
  "location": {
    "lat": 54.2,
    "lon": 37.1,
    "address": "Площадь Ленина"
  },
  "description": "Бывает...",
  "conflict": false,
  "messages": 0
}
"""

const val conflictAccident = """
{
  "id": "1",
  "created": "2021-04-15T10:32:36.000+00:00",
  "type": "OTHER",
  "resolved": "2021-04-15T10:32:36.000+00:00",
  "verified": true,
  "hidden": true,
  "creator": "string",
  "creatorNick": "123",  
  "location": {
    "lat": 54.2,
    "lon": 37.1,
    "address": "Площадь Ленина"
  },
  "description": "Бывает...",
  "conflict": true,
  "messages": 0
}
"""

const val noConflictAccident = """
{
  "id": "1",
  "created": "2021-04-15T10:32:36.000+00:00",
  "type": "OTHER",
  "resolved": "2021-04-15T10:32:36.000+00:00",
  "verified": true,
  "hidden": true,
  "creator": "string",
  "creatorNick": "123",  
  "location": {
    "lat": 54.2,
    "lon": 37.1,
    "address": "Площадь Ленина"
  },
  "description": "Бывает...",
  "conflict": false,
  "messages": 0
}
"""

const val resolveAccident = """
{
  "id": "1",
  "created": "2021-04-15T10:32:36.000+00:00",
  "type": "OTHER",
  "resolved": "2021-04-15T10:32:36.000+00:00",
  "verified": true,
  "hidden": true,
  "creator": "string",
  "creatorNick": "123",  
  "location": {
    "lat": 54.2,
    "lon": 37.1,
    "address": "Площадь Ленина"
  },
  "description": "Бывает...",
  "conflict": false,
  "messages": 0
}
"""

const val reopenAccident = """
{
  "id": "1",
  "created": "2021-04-15T10:32:36.000+00:00",
  "type": "OTHER",
  "verified": true,
  "hidden": false,
  "creator": "string",
  "creatorNick": "123",  
  "location": {
    "lat": 54.2,
    "lon": 37.1,
    "address": "Площадь Ленина"
  },
  "description": "Бывает...",
  "conflict": false,
  "messages": 0
}
"""

const val accidentFirst = """
  {
    "id": "1",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "creator": "string",
    "creatorNick": "123",
    "location": {
      "lat": 55.568,
      "lon": 37.8488,
      "address": "Площадь Ленина"
    },
    "description": "Держака нет!",
    "conflict": true,
    "messages": 0
  }
"""

const val accidentSecond = """
{
    "id": "2",
    "created": "2021-04-17T18:29:09.000+00:00",
    "type": "MOTO_PEDESTRIAN",
    "verified": false,
    "hidden": false,
    "hardness": "LIGHT",
    "creator": "607b289b63d52731fca27989",
    "creatorNick": "123",    
    "location": {
      "lat": 45.06739,
      "lon": 38.931,
      "address": "Краснодарск"
    },
    "description": "Упал, очнулся - гипс",
    "conflict": true,
    "messages": 0
}
"""

const val accidentThrid = """
{
    "id": "3",
    "created": "2021-04-17T18:29:09.000+00:00",
    "type": "STEAL",
    "verified": false,
    "hidden": false,
    "creator": "607b289b63d52731fca27989",
    "creatorNick": "123",    
    "location": {
      "lat": 55.2028,
      "lon": 37.3100001,
      "address": "string"
    },
    "description": "кто-то что-то как-то",
    "conflict": false,
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
    "creator": "string",
    "creatorNick": "123",
    "location": {
      "lat": 55.598,
      "lon": 37.8488,
      "address": "Площадь Ленина"
    },
    "description": "Держака нет!",
    "conflict": true,
    "messages": 0
  },
    {
    "id": "2",
    "created": "2021-04-17T18:29:09.000+00:00",
    "type": "MOTO_PEDESTRIAN",
    "verified": false,
    "hidden": false,
    "hardness": "LIGHT",
    "creator": "607b289b63d52731fca27989",
    "creatorNick": "123",    
    "location": {
      "lat": 45.06739,
      "lon": 38.938,
      "address": "Краснодарск"
    },
    "description": "Упал, очнулся - гипс",
    "conflict": true,
    "messages": 0
  },
    {
    "id": "3",
    "created": "2021-04-17T18:29:09.000+00:00",
    "type": "STEAL",
    "verified": false,
    "hidden": false,
    "creator": "607b289b63d52731fca27989",
    "creatorNick": "123",    
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
    "id": "4",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "MOTO_MOTO",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "UNKNOWN",
    "creator": "string",
    "creatorNick": "123",
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
    "id": "5",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "MOTO_AUTO",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "NO",
    "creator": "string",
    "creatorNick": "123",
    "location": {
      "lat": 55.584028,
      "lon": 38.38280001,
      "address": "string"
    },
    "description": "string",
    "conflict": true,
    "messages": 0
  },
  {
    "id": "6",
    "created": "2021-04-17T18:29:09.000+00:00",
    "type": "MOTO_PEDESTRIAN",
    "verified": false,
    "hidden": false,
    "hardness": "LIGHT",
    "creator": "607b289b63d52731fca27989",
    "creatorNick": "123",
    "location": {
      "lat": 45.066,
      "lon": 38.53586,
      "address": "Краснодарск"
    },
    "description": "Упал, очнулся - гипс",
    "conflict": false,
    "messages": 0
  },
  {
    "id": "7",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "HEAVY",
    "creator": "string",
    "creatorNick": "123",
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
    "id": "8",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "hardness": "LETHAL",
    "creator": "string",
    "creatorNick": "123",
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
    "id": "9",
    "created": "2021-04-17T18:29:09.000+00:00",
    "type": "OTHER",
    "verified": false,
    "hidden": false,
    "creator": "607b289b63d52731fca27989",
    "creatorNick": "123",
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
    "id": "10",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "creator": "string",
    "creatorNick": "123",
    "location": {
      "lat": 56.6228,
      "lon": 37.4609201,
      "address": "string"
    },
    "description": "string",
    "conflict": true,
    "messages": 0
  },
    {
    "id": "11",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "creator": "string",
    "creatorNick": "123",
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
    "id": "12",
    "created": "2021-04-17T18:29:09.000+00:00",
    "type": "OTHER",
    "verified": false,
    "hidden": false,
    "creator": "607b289b63d52731fca27989",
    "creatorNick": "123",
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
    "id": "13",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "creator": "string",
    "creatorNick": "123",
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
    "id": "14",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "creator": "string",
    "creatorNick": "123",
    "location": {
      "lat": 55.8,
      "lon": 37.75813201,
      "address": "string"
    },
    "description": "string",
    "conflict": true,
    "messages": 6
  },
  {
    "id": "15",
    "created": "2021-04-17T18:29:09.000+00:00",
    "type": "OTHER",
    "verified": false,
    "hidden": false,
    "creator": "607b289b63d52731fca27989",
    "creatorNick": "123",
    "location": {
      "lat": 55.69,
      "lon": 37.5913201,
      "address": "string"
    },
    "description": "кто-то что-то как-то",
    "conflict": false,
    "messages": 8
  },{
    "id": "16",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "creator": "string",
    "creatorNick": "123",
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
    "id": "17",
    "created": "2021-04-01T13:51:42.000+00:00",
    "type": "OTHER",
    "resolved": "2021-04-01T13:51:42.000+00:00",
    "verified": true,
    "hidden": true,
    "creator": "string",
    "creatorNick": "123",
    "location": {
      "lat": 56.80,
      "lon": 37.94132,
      "address": "string"
    },
    "description": "string",
    "conflict": true,
    "messages": 0
  }
]
"""
