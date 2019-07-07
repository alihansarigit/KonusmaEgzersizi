package com.yuhdeveloper.konusmaegzersizi


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuhdeveloper.konusmaegzersizi.Adapterss.Rec_Adapter_Statistic_Details


class WordsFragment : Fragment() {

    lateinit var recylerView: RecyclerView
    var isCorrect: Boolean = false

    lateinit var list: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_words, container, false)
        recylerView = view.findViewById(R.id.words_rec)
        recylerView.layoutManager = GridLayoutManager(view.context, 2)
        var recAdapter = Rec_Adapter_Statistic_Details(list, isCorrect)
        recylerView.adapter = recAdapter

//        if(isCorrect){
//            correct()
//        }
        //initStep(view)
        return view
    }

    private fun initStep(view: View) {
        recylerView = view.findViewById(R.id.words_rec)
        recylerView.layoutManager = GridLayoutManager(view.context, 2)
    }

    private fun correct(){
        var recAdapter = Rec_Adapter_Statistic_Details(list, isCorrect)
        recylerView.adapter = recAdapter
    }
    private fun pass(){
        var recAdapter = Rec_Adapter_Statistic_Details(list, isCorrect)
        recylerView.adapter = recAdapter
    }
    fun listAdd(list: ArrayList<String>, isCorrect: Boolean) {
        var recAdapter = Rec_Adapter_Statistic_Details(list, isCorrect)
        recylerView.adapter = recAdapter
    }

}
