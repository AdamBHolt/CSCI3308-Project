#!/usr/bin/env python

import os
import os.path
import MySQLdb

music = []
picture = []
video = []
inaction = []
locations = {}

db = MySQLdb.connect(host="", user = "", passwd = "", db = "") #fill in w/ proper values
cur = db.cursor()

def mediaSort(directory): #sorts files in given directory into 4 distinct lists. Gives full location if fdirect is 1
    for x in os.listdir(directory):
        longx = directory+"/"+x
        extension = os.path.splitext(x)[1] #scrapes the extension for file x
        if os.path.isfile(longx) == 0 and x != ".git" and x != "home": #if a folder is found, run mediaSort on said folder
            mediaSort(longx)
            continue
        if extension in ['.jpg', '.png', '.gif']:
            picture.append(x)
            locations[x] = longx;
            continue
        if extension in ['.mov', '.ogv', '.wmv']:
            video.append(x)
            locations[x] = longx;
            continue
        if extension in ['.mp3', '.wav', '.ogg']:
            music.append(x)
            locations[x] = longx;
            continue
        if extension in ['.txt', '.py']: #easy way to disregard certain extensions
            continue
        else:
            inaction.append(x) #in case we want to list files we do nothing with

    
def fillTable(medialist, listname):
    for media in medialist:
        cur.execute("INSERT INTO %s (name, location) VALUES (%%s, %%s)" % listname, (media, locations[media]))

def parityCheck(medialist, listname): #returns 1 if values in a list are the same as db
    cur.execute("SELECT Name from %s" %listname)
    parity = []
    for row in cur:
        parity.append(row[0])
    for media in medialist:
        if media in parity:
            continue
        else:
            return 0
    for media in parity:
        if media in medialist:
            continue
        else:
            return 0
    return 1

def main ():
    mediaSort(os.getcwd())
    if parityCheck(picture, "pictures") == 0:
        cur.execute("TRUNCATE pictures")
        fillTable(picture, "pictures")
    if parityCheck(music, "music") == 0:
        cur.execute("TRUNCATE music")
        fillTable(music, "music")
    if parityCheck(video, "videos") == 0:
        cur.execute("TRUNCATE videos")
        fillTable(video, "videos")
    db.close()
        
    
        
    
