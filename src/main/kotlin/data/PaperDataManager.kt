package org.example.data

import org.example.data.modal.PaperData
import org.example.logger

object PaperDataManager {
    var isPaperStarted: Boolean = false
    private val papers: MutableList<PaperData> = ArrayList()

    fun addPaperData(paperData: PaperData) {
        papers.add(paperData)
    }

    fun getPaperDataByWriter(id: String): ArrayList<PaperData> {
        val rs = ArrayList<PaperData>()
        for (paperData in papers) {
            if (paperData.user.id == id) {
                rs.add(paperData)
            }
        }

        return rs
    }

    fun getPaperDataByTarget(id: String): ArrayList<PaperData> {
        val rs = ArrayList<PaperData>()
        for (paperData in papers) {
            if (paperData.target.id == id) {
                rs.add(paperData)
            }
        }

        return rs
    }

    fun removePaperData(paperData: PaperData) {
        papers.remove(paperData)
    }

    fun getPapers(): List<PaperData> {
        return papers
    }

    fun clearPaperData() {
        papers.clear()
    }
}