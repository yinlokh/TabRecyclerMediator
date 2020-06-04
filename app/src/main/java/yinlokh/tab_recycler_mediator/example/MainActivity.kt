/*
 *    Copyright 2020 Yin Lok Ho
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package yinlokh.tab_recycler_mediator.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.example_header.view.*
import yinlokh.tab_recycler_mediator.R
import yinlokh.tab_recycler_mediator.TabRecyclerMediator

class MainActivity : AppCompatActivity() {

    val adapter = TestRecyclerAdapter()
    val linearLayoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.adapter = adapter
        recycler.layoutManager = linearLayoutManager

        for (i in 1..10) {
            tab_layout.addTab(tab_layout.newTab().setText(i.toString()))
        }

        val mediator = TabRecyclerMediator(recycler, tab_layout)
        mediator.listener = object : TabRecyclerMediator.Listener {
            override fun getRecyclerPositionForTab(tab: Int): Int {
                return tab * 3
            }

            override fun getTabForRecyclerPosition(pos: Int): Int {
                return pos / 3
            }
        }
    }

    class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val text = itemView.text
    }
}
