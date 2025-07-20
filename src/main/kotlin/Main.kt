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
    println("Your Event: ${x.printEvent()} is added")
    val flag=true
    while (flag){
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

}

fun findEvent() {
    println("============================ Find Event =============================\n\n")


}

fun viewEvents() {
    println("=========================== View Events =============================\n\n")

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