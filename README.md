# TabRecyclerMediator
Mediator for syncing between a tab layout and a vertical / horizontal scrolling RecyclerView.

## Usage
Add TabRecyclerMediator.kt to project, set TabLayout and RecyclerView.  
RecyclerView must have a LinearLayoutManager.
Listener provides mapping between tab position and recycler position.

### Example
```kotlin
// add decoration to recycler
recycler.addItemDecoration(decoration)

// add header adapter to decoration
 val mediator = TabRecyclerMediator(recycler, tab_layout)
        mediator.listener = object : TabRecyclerMediator.Listener {
            override fun getRecyclerPositionForTab(tab: Int): Int {
                return tab * 3
            }

            override fun getTabForRecyclerPosition(pos: Int): Int {
                return pos / 3
            }
        }
```

# License
     Copyright 2020 Yin Lok Ho
 
     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.

