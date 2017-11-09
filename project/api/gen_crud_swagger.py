#!/usr/bin/env python
# -*- coding: utf-8 -*-

'''
~Gros
'''

import sys

if len(sys.argv) < 4:
    print "Usage: python {} normal name tag".format(sys.argv[0])
    print "Usage: python {} nested name_nested name tag".format(sys.argv[0])
    sys.exit(1)

template = '''
  /{name}s:
    get:
      tags:
        - {tag}
      operationId: get{name_upper}s
      summary: Returns list of {name_upper}s
      consumes:
        - application/json
      produces:
        - application/json
      responses:
        200:
          description: ""
          schema:
            type: array
            items:
              $ref: "#/definitions/{name_upper}"
        401:
          $ref: "#/responses/Unauthorized"
        500:
          $ref: "#/responses/ServerError"
    post:
      tags:
        - {tag}
      operationId: create{name_upper}
      summary: Create new {name}
      consumes:
        - application/json
      produces:
        - application/json
      parameters:
        - in: body
          name: {name}
          description: {name_upper} to create
          schema:
            $ref: "#/definitions/{name_upper}"
      responses:
        200:
          description: ""
          schema:
            type: integer
            format: int32
        401:
          $ref: "#/responses/Unauthorized"
        500:
          $ref: "#/responses/ServerError"

  /{name}s/{{{name}Id}}:
    parameters:
      - in: path
        name: {name}Id
        type: integer
        required: true
    get:
      tags:
        - {tag}
      operationId: get{name_upper}
      summary: Returns {name_upper}
      consumes:
        - application/json
      produces:
        - application/json
      responses:
        200:
          description: ""
          schema:
            $ref: "#/definitions/{name_upper}"
        401:
          $ref: "#/responses/Unauthorized"
        500:
          $ref: "#/responses/ServerError"
    put:
      tags:
        - {tag}
      operationId: update{name_upper}
      summary: Update existing {name}
      consumes:
        - application/json
      produces:
        - application/json
      parameters:
        - in: body
          name: {name}
          description: {name_upper} to update
          schema:
            $ref: "#/definitions/{name_upper}"
      responses:
        200:
          description: "Updated"
        401:
          $ref: "#/responses/Unauthorized"
        500:
          $ref: "#/responses/ServerError"

    delete:
      tags:
        - {tag}
      operationId: delete{name_upper}
      summary: Delete {name}
      consumes:
        - application/json
      produces:
        - application/json
      responses:
        200:
          description: "Deleted"
        401:
          $ref: "#/responses/Unauthorized"
        500:
          $ref: "#/responses/ServerError"
'''


template_nested = '''
  
  /{name_nested}s/{{{name_nested}Id}}/{name}s:
    parameters:
      - in: path
        name: {name_nested}Id
        type: integer
        required: true
    get:
      tags:
        - {tag}
      operationId: get{name_nested_upper}{name_upper}s
      summary: Returns {name}s belonging to given {name_nested}
      consumes:
        - application/json
      produces:
        - application/json
      responses:
        200:
          description: ""
          schema:
            type: array
            items:
              $ref: "#/definitions/{name_nested_upper}{name_upper}"
        401:
          $ref: "#/responses/Unauthorized"
        500:
          $ref: "#/responses/ServerError"
    post:
      tags:
        - {tag}
      operationId: create{name_nested_upper}{name_upper}s
      summary: Create new {name_nested_upper}{name_upper} for given {name_nested}
      consumes:
        - application/json
      produces:
        - application/json
      parameters:
        - in: body
          name: order
          description: {name_nested_upper}{name_upper} to create
          schema:
            $ref: "#/definitions/{name_nested_upper}{name_upper}"
      responses:
        200:
          description: ""
          schema:
            type: integer
            format: int32
        401:
          $ref: "#/responses/Unauthorized"
        500:
          $ref: "#/responses/ServerError"


  /{name_nested}s/{{{name_nested}Id}}/{name}s/{{{name_nested_upper}{name_upper}Id}}:
    parameters:
      - in: path
        name: {name_nested}Id
        type: integer
        required: true
      - in: path
        name: {name_nested_upper}{name_upper}Id
        type: integer
        required: true
    get:
      tags:
        - {tag}
      operationId: get{name_nested_upper}{name_upper}
      summary: Returns {name} belonging to given {name_nested}
      consumes:
        - application/json
      produces:
        - application/json
      responses:
        200:
          description: ""
          schema:
            $ref: "#/definitions/{name_nested_upper}{name_upper}"
        401:
          $ref: "#/responses/Unauthorized"
        500:
          $ref: "#/responses/ServerError"
    put:
      tags:
        - {tag}
      operationId: update{name_nested_upper}{name_upper}
      summary: Update existing {name_nested_upper}{name_upper}
      consumes:
        - application/json
      produces:
        - application/json
      parameters:
        - in: body
          name: {name_nested_upper}{name_upper}
          description: {name_nested_upper}{name_upper} to update
          schema:
            $ref: "#/definitions/{name_nested_upper}{name_upper}"
      responses:
        200:
          description: "Updated"
        401:
          $ref: "#/responses/Unauthorized"
        500:
          $ref: "#/responses/ServerError"

    delete:
      tags:
        - {tag}
      operationId: delete{name_nested_upper}{name_upper}
      summary: Delete {name_nested_upper}{name_upper}
      consumes:
        - application/json
      produces:
        - application/json
      responses:
        200:
          description: "Deleted"
        401:
          $ref: "#/responses/Unauthorized"
        500:
          $ref: "#/responses/ServerError"
'''

nested = False
if 'nested' in sys.argv[1].lower():
    nested = True

if nested:
    name_nested = sys.argv[2]
    name = sys.argv[3]
    name_upper = name[0].upper() + name[1:]
    name_nested_upper = name_nested[0].upper() + name_nested[1:]
    tag = sys.argv[4]
    print template_nested.format(name=name, name_upper=name_upper, name_nested=name_nested, name_nested_upper=name_nested_upper, tag=tag) 
    print "NESTED"
else:
    name = sys.argv[2]
    name_upper = name[0].upper() + name[1:]
    tag = sys.argv[3]
    print template.format(name=name, name_upper=name_upper, tag=tag)