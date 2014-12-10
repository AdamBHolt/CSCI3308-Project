#!/usr/bin/env python

## @mainpage Raspberry Pi Media Server Media Scraper
# @author Peter Goudy, Adam Holt, Marc Simpson
# @date December 09, 2014
# @details This program, when run, will find all media files (by their extension), and sort them into a database along with their location.
# @warning In the sense of security, the database information has been omitted. Enter your own values before running this program. 


import os
import os.path
import MySQLdb
import argparse
##@cond
music = []
picture = []
video = []
inaction = []
locations = {}

db = MySQLdb.connect(host="", user = "", passwd = "", db = "")
cur = db.cursor()
##@endcond

## This method sorts all media in a given directory (as well as all directories below it) into 4 distinct pre-made lists. 
# Additional extensions can be added to the search by adding them to the respective lists.
# @param directory This is the directory to sort in.
def mediaSort(directory):
    for x in os.listdir(directory):
        longx = directory+"/"+x
        extension = os.path.splitext(x)[1]
        if os.path.isfile(longx) == 0 and x != ".git" and x != "home": #if a folder is found, run mediaSort on said folder
            mediaSort(longx)
            continue
        if extension in ['.jpg', '.png', '.gif']:
            picture.append(x)
            locations[x] = longx;
            continue
        if extension in ['.mov', '.ogv', '.wmv', '.m4v']:
            video.append(x)
            locations[x] = longx;
            continue
        if extension in ['.mp3', '.wav', '.ogg']:
            music.append(x)
            locations[x] = longx;
            continue
        if extension in ['.txt', '.py']: 
            continue
        else:
            inaction.append(x)

## This method populates a premade MySQL table with the name and location of a filetype.
# @param medialist This is a list of filenames of a given media type
# @param listname This is the name of both the media list, and MySQL table
def fillTable(medialist, listname):
    for media in medialist:
        cur.execute("INSERT INTO %s (name, location) VALUES (%%s, %%s)" % listname, (media, locations[media]))

## This method will check a list against a MySQL table to see if they contain the same items.
# @param medialist This is a list of filenames of a given media type
# @param listname This is the name of both the media list, and MySQL table
# @return Returns 1 if they contain the same items, else 0.
def parityCheck(medialist, listname):
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

## @brief This method will parse the arguements, then call mediaSort to populate the media lists. From there, it will call parityCheck, and should any of those return 0, run fillTable on them.
# @brief The connection to MySQL will then be terminated.
def main ():
    parser = argparse.ArgumentParser()
    parser.add_argument("-d", "--directory", help="Sort in a given directory. If blank, will run in current directory.", nargs='?')
    args = parser.parse_args()
    if args.directory:
        mediaSort(args.directory)
    else:
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
    db.commit()
    db.close()
main()
        
    
        
    
