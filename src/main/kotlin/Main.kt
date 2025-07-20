package org.example

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*
import kotlin.system.exitProcess

var counter = 0
var events: MutableList<Event> = ArrayList()

fun main() {
    println("=====================================================================\n")
    println("Welcome, User!\n")
    menu()
}

fun menu() {
    println("============================= Menu ==================================\n")

    println("Please choose a valid option: ")
    println("1. Add a new Event")
    println("2. delete an Event")
    println("3. View Events")
    println("4. Find an Event")
    println("5. Exit program")
    print("Your Choice: ")
    val input = readLine().toString()
    when (input) {
        "1" -> addEvent()
        "2" -> deleteEvent()
        "3" -> viewEvents()
        "4" -> findEvent()
        "5" -> {
            exitProcess(0)
        }

        else -> {
            println("Invalid Input")
            menu()
        }
    }
}

fun addEvent() {
    println("============================= Add Event =============================\n")
    lateinit var date: LocalDate
    lateinit var time: LocalTime
    lateinit var message: String
    while (true) {
        try {
            print("Enter a date (yyyy-MM-dd): ")
            val input = readLine().toString()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            date = LocalDate.parse(input, formatter)
            println("You entered: $date")
            break
        } catch (e: Exception) {
            println("Invalid date format. Please use yyyy-MM-dd.")
        }
    }




    while (true) {
        try {
            print("Enter a time (HH:mm): ")
            val input = readLine()
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            time = LocalTime.parse(input, formatter)
            println("You entered: $time")
            break
        } catch (e: DateTimeParseException) {
            println("Invalid time format. Please use HH:mm (e.g., 14:30).")
        }
    }


    print("Do you want to add a comment about this event? (Y/N) :")
    while (true) {
        val input = readLine().toString()
        when (input) {
            "Y" -> {
                println("Enter comment: ")
                message = readLine().toString()
                break
            }

            "y" -> {
                println("Enter comment: ")
                message = readLine().toString()
                break
            }

            "N" -> {
                message = ""
                break
            }

            "n" -> {
                message = ""
                break
            }

            else -> {
                println("Invalid input.")
                print("Please enter a valid option (Y/N) : ")
            }
        }
    }
    val x: Event = Event(++counter, date, time, message)
    events.add(x)
    print("Your Event: ")
    x.printEvent()
    println("is added")
    while (true) {
        println("Do you want to add another event or exit to main menu?")
        println("1. Add an Event")
        println("2. Main Menu")
        val input = readLine().toString()
        when (input) {
            "1" -> {
                addEvent()
                return
            }

            "2" -> {
                menu()
                return
            }

            else -> {
                println("Invalid input.")
            }
        }
    }

}

fun deleteEvent() {
    println("=========================== Delete Event ============================\n\n")
    while (true) {
        println("Enter a valid Event ID : (to go to main menu enter 0 )")
        val input = readLine()?.toInt()
        if (input == 0) {
            menu()
            return
        }
        if (input != null) {
            val ev = findEventbyId(input)
            if (ev != null) {
                print("Event: ")
                events[ev].printEvent()
                println(" has been deleted!")
                events.removeAt(ev)
                menu()
                return
            } else {
                println("Invalid ID")
                continue
            }
        }
        println("Invalid input!")
    }


}

fun findEvent() {
    println("============================ Find Event =============================\n\n")
    while (true) {
        println("Please enter a valid option: ")
        println("1. Find Event by ID")
        println("2. Find Event by Date")
        println("3. Find Event by Time")
        println("4. Return to main menu")
        val input = readLine().toString()
        when (input) {
            "1" -> {
                while (true) {
                    println("Enter a valid Event ID : (to go backward enter 0 )")
                    val input = readLine()?.toInt()
                    if (input == 0) {
                        findEvent()
                        return
                    }
                    if (input != null) {
                        val ev = findEventbyId(input)
                        if (ev != -1) {
                            println("Event: ")
                            events[ev].printEvent()
                            println(" has been found!")
                            menu()
                            return
                        } else {
                            println("Invalid ID")
                            continue
                        }
                    }
                    println("Invalid input!")
                }
            }

            "2" -> {
                while (true) {
                    try {
                        println("Enter a date (yyyy-MM-dd): (to go backward enter 0 )")
                        val input = readLine().toString()
                        if (input == "0") {
                            findEvent()
                            return
                        }
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        val date = LocalDate.parse(input, formatter)
                        val event = findEventbyDate(date)
                        if (event != null) {
                            println("Found Events are : ")
                            event.forEach { it.printEvent() }
                            menu()
                            return
                        } else {
                            println("There is no event with this \"$date\" date")
                        }

                    } catch (e: Exception) {
                        println("Invalid date format. Please use yyyy-MM-dd.")
                    }
                }
            }

            "3" -> {
                while (true) {
                    try {
                        println("Enter a time (HH:mm): (to go backward enter 0 )")
                        val input = readLine().toString()
                        if (input == "0") {
                            findEvent()
                            return
                        }
                        val formatter = DateTimeFormatter.ofPattern("HH:mm")
                        val time = LocalTime.parse(input, formatter)
                        val event = findEventbyTime(time)
                        if (event != null) {
                            println("Events found are : ")
                            event.forEach { it.printEvent() }
                            menu()
                            return
                        } else {
                            println("there is no events found in that time")
                        }
                    } catch (e: DateTimeParseException) {
                        println("Invalid time format. Please use HH:mm (e.g., 14:30).")
                    }
                }
            }

            "4" -> {
                menu()
            }
        }
    }

}

fun findEventbyId(id: Int): Int {
    return events.indexOfFirst { it.id == id }
}

fun findEventbyDate(localDate: LocalDate): List<Event> {
    return events.filter { event -> event.date == localDate }
}

fun findEventbyTime(localTime: LocalTime): List<Event> {
    return events.filter { it.time == localTime }
}

fun viewEvents() {
    println("=========================== View Events =============================\n\n")
    if (events.size == 0) {
        println("There is no Events to display")
    }
    for (event in events) {
        event.printEvent()
    }
    menu()
}

class Event(id: Int, date: LocalDate, time: LocalTime, message: String = "") {
    val id: Int = id
    val date: LocalDate = date
    val message: String = message
    val time: LocalTime = time
    fun printEvent() {
        print("ID: $id, Date: $date, Time: $time")
        if (message.isNotEmpty()) {
            println(", Message: $message")
        } else println()
    }

}