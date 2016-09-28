curl -XPUT 'http://localhost:9200/moviesindex/'

curl 'http://localhost:9200/moviesindex/' -X POST -d '{

"settings": {
    "analysis": {
      "analyzer": {
        "semicolon": {
          "type": "pattern",
          "pattern": "[;]+"
        }
      }
    }
  }
}'

curl 'http://localhost:9200/moviesindex/_mapping/candidate' -X POST -d '{
  "properties": {
    "genre": {
      "type": "string",
      "index": "not_analyzed"
    },
     "title": {
      "type": "string",
      "index": "not_analyzed"
    },
     "actors": {
      "type": "string",
      "index": "not_analyzed"
    },
     "actresses": {
      "type": "string",
      "index": "not_analyzed"
    },
     "writers": {
      "type": "string",
      "index": "not_analyzed"
    },
    "release-date": {
      "type": "date"
    },
    "running-times": {
      "type": "integer"
    }
   }

 }'
