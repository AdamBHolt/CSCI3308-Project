#!/usr/bin/env python
# Overall, pretty rough. What it does is lists files in three distinct categories (music, pics, video) and writes the names of said files into three files to use later. Can be modified, but is a good starting point.

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
    print "Working in:" + directory
    for x in os.listdir(directory):
        longx = directory+"/"+x
        extension = os.path.splitext(x)[1] #scrapes the extension for file x
        if os.path.isfile(longx) == 0 and x != ".git": #if a folder is found, run mediaSort on said folder
            mediaSort(longx)
            continue
        if extension in ['.jpg', '.png', '.gif']: #list so can easily be added to
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
    cur.execute("truncate %s" % listname)
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
    
    

def test (): #personal test code. Runs mediasort in directory of list.py, and fills out a table. Will be removed eventually.
    mediaSort(os.getcwd())
    print parityCheck(picture, "pictures")
    #fillTable(picture, "pictures")
    #fillTable(music, "music")
    #fillTable(video, "videos")
    db.close()
    
test()
