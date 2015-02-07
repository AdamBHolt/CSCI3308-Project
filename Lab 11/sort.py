#!/usr/bin/env python
# Overall, pretty rough. What it does is lists files in three distinct categories (music, pics, video) and writes the names of said files into three files to use later. Can be modified, but is a good starting point.

import os
import os.path

music = []
picture = []
video = []
inaction = []

def mediaSort(directory, fdirect): #sorts files in given directory into 4 distinct lists. Gives full location if fdirect is 1
    print "Working in:" + directory
    for x in os.listdir(directory):
        longx = directory+"/"+x
        if fdirect == True:
            x = longx
        extension = os.path.splitext(x)[1] #scrapes the extension for file x
        if os.path.isfile(longx) == 0: #if a folder is found, run mediaSort on said folder
            mediaSort(longx, fdirect)
            continue
        if extension in ['.jpg', '.png', '.gif']: #list so can easily be added to
            picture.append(x)
            continue
        if extension in ['.mov', '.ogv', '.wmv']:
            video.append(x)
            continue
        if extension in ['.mp3', '.wav', '.ogg']:
            music.append(x)
            continue
        if extension in ['.txt', '.py']: #easy way to disregard certain extensions
            continue
        else:
            inaction.append(x) #in case we want to list files we do nothing with
        
def createFile(inlist, listname): #creates a .txt file from a list, given said list and filename
    outfile = open(listname, 'w')
    inlist.sort() #might be unneeded
    for x in inlist:
        outfile.write(x + "\n")
    print "%s files in " %len(inlist) + listname

def test (): #personal test code. Runs mediasort in directory of list.py, and creates text files. Will be removed eventually.
    mediaSort(os.getcwd(), 0)
    createFile(picture, "pictures.txt")
    createFile(video, "videos.txt")
    createFile(music, "music.txt")
    if len(inaction) != 0: #will probably move to a function later
        print "No action taken on:"
        for x in inaction:
            print x
    
test()
