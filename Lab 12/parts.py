class CPU:
    def __init__(self):
        self.freq="3.5GHz"
        self.coreNum="4"

    def check(self):
        print "Checking CPU...."
        return 1

    def getInfo(self):
        print "CPU info:\n  Frequency: "+self.freq+"\n  Number of cores: "+self.coreNum

class Memory:
    def __init__(self):
        self.gen="DDR4"
        self.size="4GB"
        
    def load(self):
        print "Loading memory..."
        return 1

    def getInfo(self):
        print "Memory info:\n  Memory Type: "+self.gen+"\n  Size: "+self.size

class HardDisk:
    def __init__(self):
        self.fs="NTFS"
        self.volume="1TB"

    def mount(self):
        print "Mounting disk..."
        return 1

    def getInfo(self):
        print "Hard Disk info:\n  File System: "+self.fs+"\n  Volume: "+self.volume

