package hw2.service.ui.command

import hw2.exception.ToPreviousWindowException

class ToPreviousWindowCommand : Command {
    override fun run() {
        throw ToPreviousWindowException("")
    }
}