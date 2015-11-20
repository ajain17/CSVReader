import com.opencsv.CSVReader
import groovy.io.FileType
def list = []
def dir = new File("/Users/ayushi.jain/Desktop/csvs")
dir.eachFileRecurse(FileType.FILES){ file -> list << file}
List<String[]> nextLine
list.each{ path ->
    CSVReader csv = new CSVReader(new FileReader(path));
    nextLine = csv.readAll()
    nextLine = nextLine.findAll{ (!it[0].isEmpty() && it.length!=1)}    //filter all empty rows
    println nextLine
}



