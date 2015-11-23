import com.opencsv.CSVReader
import groovy.io.FileType
public static void main(String[] args){
    if(args)
        updateDtaFlags(args[0],args[1],args[2],args[3])
    else
        println "Please pass username, password and url"
}

void updateDtaFlags(String username, String password, String url, String csvPath) {
    def list = []
    def requestUrl
    def dtaFlag
    String channelNo
    def dir = new File(csvPath)
    dir.eachFileRecurse(FileType.FILES) { file -> list << file }
    List<String[]> nextLine
    list.each { path ->
        CSVReader csv = new CSVReader(new FileReader(path))
        nextLine = csv.readAll().drop(1)
        nextLine = nextLine.findAll { (!it[0].isEmpty() && it.length != 1) }    //filter all empty rows
        nextLine.each {
            csvRow ->
                if (!csvRow[5].isEmpty()) {
                    channelNo = csvRow[2]
                    def (lineupName, lineupId) = csvRow[0].tokenize('-')
                    ((csvRow[5].equalsIgnoreCase("true")) ? (dtaFlag = true) : (dtaFlag = false))
                    requestUrl = url + lineupName.toUpperCase() + "/" + lineupName.toLowerCase() + "-" + lineupId + "/jcr%3Acontent/channel-list/channel_lineup_" + channelNo
                    def process = "curl -k -X POST -FDTA=${dtaFlag}  -u ${username}:${password} http://${requestUrl}"
                    println process                //to see the CURL request
                    process.execute().text
                }
        }
    }
}

