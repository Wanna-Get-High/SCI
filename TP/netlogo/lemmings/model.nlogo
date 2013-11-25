__includes ["IODA_2_2.nls"]

extensions [table ioda]

breed [walls wall]
breed [entrances entrance]
breed [exits exit]
breed [ground]
breed [blasts blast]
breed [lemmings lemming]

globals [ 
  nb-saved ; nb of lemmings that have already been saved
  nb-to-create ; nb of lemming to create in this level
  nb-to-save ; nb of lemmings to save in order to succeed
  creation-period ; nb of ticks between two creations
  available-aptitudes ; table of available aptitudes with the corresponding quantities
  selected-agent ; agent selected by left click
  quick-end? ; player decides to terminate level
]

walls-own [ destructible? ]
blasts-own [ strength ]
lemmings-own [ moving-state speed fall-length last-dx delta-x delta-y aptitudes strength ]

to setup
  clear-all
  init-world
  ioda:load-interactions "interactions.txt"
  ioda:load-matrices "matrix.txt" " \t(),"
  ioda:setup
  ioda:set-metric "Moore"
  reset-ticks
end

to go
  tick
  ioda:go
  if (not any? lemmings) and (nb-to-create = 0)
    [ ifelse nb-saved >= nb-to-save 
        [ user-message "CONGRATULATIONS !" 
          set level level + 1 
          ifelse user-yes-or-no? "WANT TO CONTINUE ?"
            [ setup ]
            [ stop ]
        ]
        [ user-message "TRY AGAIN !" stop ]
    ]
end

to init-world
  set base-speed 0.2
  set selected-agent nobody
  set quick-end? false
  set-default-shape walls "tile brick"
  set-default-shape lemmings "person"
  set-default-shape entrances "square 2"
  set-default-shape exits "flag"
  set-default-shape ground "tile water"
  set-default-shape blasts "star"
  read-level (word "level" level ".txt")
end


to read-level [ filename ]
  if (not file-exists? filename) 
    [ user-message (word "MAP NOT FOUND: " filename)
      stop 
    ]
  file-open filename
  let s read-from-string file-read-line ; list with width and height
  resize-world 0 (first s - 1)  (1 - last s) 0
  set s read-from-string file-read-line ; list with nb-to-create, creation-period and nb-to-save
  set nb-to-create first s 
  set creation-period item 1 s
  set nb-to-save last s
  set s read-from-string file-read-line ; available aptitudes with quantities
  set available-aptitudes table:from-list s
  let x 0 let y 0
  while [(y >= min-pycor) and (not file-at-end?)]
    [ set x 0 
      set s file-read-line
      while [(x <= max-pxcor) and (x < length s)]
        [ ask patch x y [ create-agent (item x s) ]
          set x x + 1 ]
      set y y - 1 ]
  file-close
end

to create-agent [ char ]
  ifelse (char = "X") 
    [ sprout-walls 1 [ init-wall false ] ]
    [ ifelse (char = "x") 
        [ sprout-walls 1 [ init-wall true ] ]
        [ ifelse (char = "O")
            [ sprout-exits 1 [ init-exit ]]
            [ ifelse (char = "I")
                [ sprout-entrances 1 [ init-entrance ]]
                [ ifelse (char = ".")
                    [ sprout-ground 1 [ init-ground ]]
                    [ ;;;;;; other agents ? 
                    ]
                ]
            ]
        ]
    ]
end


to init-wall [ d ]
  set destructible? d
  set heading 0 
  set color ifelse-value destructible? [ blue - 4 ] [ cyan - 4 ]
end

to init-ground
  set color brown 
end

to init-blast [ h ]
  ioda:init-agent
  set heading h
  set color orange
  set strength 3
end

to init-entrance
  set heading 0 
  set color blue 
end

to init-exit
  set heading 0
  set color lime
end

to init-lemming
  ioda:init-agent
  set strength 30
  set moving-state 0
  set fall-length 0
  set heading 0 
  set color yellow
  set speed base-speed
  set delta-x 1
  set last-dx delta-x
  set delta-y 0
  set aptitudes []
end

to-report free-place? [ p ]
  report not (any? walls-on p or any? ground-on p)
end

to-report nb-aptitudes [ apt ]
  report ifelse-value table:has-key? available-aptitudes apt
    [ table:get available-aptitudes apt ]
    [ 0 ]
end

to select-lemming
  if selected-agent != nobody
    [ watch selected-agent ]
  if (mouse-down?)
    [ let l lemmings with [(distancexy mouse-xcor mouse-ycor < 1) and (not member? current-aptitude aptitudes) ]
      if any? l 
        [ set selected-agent one-of l with-min [ distancexy mouse-xcor mouse-ycor ]]
    ]
end

to take-aptitude [ apt ]
  set aptitudes fput apt aptitudes
  table:put available-aptitudes apt (table:get available-aptitudes apt - 1)
end

to-report has-aptitude? [ apt ]
  report member? apt aptitudes
end






; PRIMITIVES IMPLEMENTED BY ENTRANCES

to-report entrances::time-to-create?
  report ticks mod creation-period = 0
end

to-report entrances::not-finished?
  report nb-to-create > 0
end

to entrances::produce-creature
  hatch-lemmings 1 [ init-lemming ]
  set nb-to-create nb-to-create - 1
end


; PRIMITIVES IMPLEMENTED BY EXITS

to exits::filter-neighbors
  ioda:filter-neighbors-on-patches (patch-set patch-here)
end

to exits::rescue
  set nb-saved nb-saved + ifelse-value is-list? ioda:my-target [length ioda:my-target] [1]
end



; PRIMITIVES IMPLEMENTED BY LEMMINGS

to lemmings::die
  ioda:die
end

to-report lemmings::no-obstacle-ahead?
  let p patch-at (delta-x * speed) (delta-y * speed)
  report (free-place? p) and (not any? (lemmings-on p) with [ lemmings::is-obstacle? ])
end

to-report lemmings::is-obstacle?
  report lemmings::is-blocker?
end

to-report lemmings::moving?
  report (delta-x != 0) or (delta-y != 0) 
end

to lemmings::advance
  setxy (xcor + delta-x * speed) (ycor + delta-y * speed)
end

to lemmings::change-shape
  ifelse (delta-x >= 0)
    [ set shape (word "person-" (moving-state + 1)) ]
    [ set shape (word "personr-" (moving-state + 1)) ]
  set moving-state (moving-state + delta-x + 9) mod 9
end

to-report lemmings::is-blocker?
  report has-aptitude? "blocker" 
end

to-report lemmings::not-blocker?
  report not has-aptitude? "blocker"
end

to lemmings::reverse-direction
  set delta-x (- delta-x)
  set delta-y (- delta-y)
end

to-report lemmings::not-falling?
  report delta-y = 0
end

to-report lemmings::nothing-below?
  report free-place? patch-at 0 (-0.5 - speed)
end

to lemmings::filter-neighbors
  ioda:filter-neighbors-in-radius 1
end

to lemmings::fall
  if (delta-x != 0) [ set last-dx delta-x ]
  set delta-x 0
  set delta-y -1
  
  set speed 1
  set fall-length fall-length + 1
end

to-report lemmings::something-below?
  report not lemmings::nothing-below?
end

to-report lemmings::falling?
  report delta-y < 0
end

to-report lemmings::long-fall?
  report fall-length >= 4
end

to lemmings::crash
  set delta-x 0 set delta-y 0 set shape "splash" set color red
end

to lemmings::recover-direction
  set delta-y 0 set delta-x last-dx set speed base-speed set fall-length 0
end

to-report lemmings::crashed?
  report shape = "splash"
end

to-report lemmings::still-strength?
  report strength > 0
end

to lemmings::decrease-strength
  set strength strength - 1
end

to-report lemmings::low-strength?
  report strength <= 0
end

to-report lemmings::selected?
  report self = selected-agent
end

to lemmings::unselect
  set selected-agent nobody
end

to-report lemmings::blocker-tool?
  report current-aptitude = "blocker"
end

to-report lemmings::enough-aptitudes?
  report nb-aptitudes current-aptitude > 0
end

to lemmings::add-blocker-aptitude
  take-aptitude "blocker" 
  set last-dx delta-x
  set delta-x 0
  set shape "man standing"
end

to lemmings::add-floater-aptitude
  take-aptitude "floater"
  set color green
end

to-report lemmings::end-of-game?
  report quick-end?
end

; ------------------------------------------------------------ ;
;                             FLOATER
; ------------------------------------------------------------ ;

to-report lemmings::floater-tool?
  report current-aptitude = "floater"
end

to-report lemmings::not-floater?
  report not has-aptitude? "floater"
end

to-report lemmings::floater?
  report has-aptitude? "floater"
end

to lemmings::float
    set shape "man floating"
    if (delta-x != 0) [set last-dx delta-x]
    set delta-x 0
    set delta-y -1
    set speed 0.1
end

; ------------------------------------------------------------ ;
;                             DIGGER
; ------------------------------------------------------------ ;
to lemmings::dig
  
end

to-report lemmings::is-ground?
  ; -0.5 car le digger est au milieu
  ; - speed pour taper sur l'agent
  report ground-on patch-at 0 (-0.5 - speed)
end

to-report lemmings::digger-tool?
  report current-aptitude = "digger"
end

to-report lemmings::not-digger?
   report not has-aptitude? "digger"
end

to lemmings::add-digger-aptitude
  take-aptitude "digger"
  set last-dx delta-x
  set delta-x 0
  set color blue
end
@#$#@#$#@
GRAPHICS-WINDOW
496
11
756
167
-1
-1
25.0
1
10
1
1
1
0
1
1
1
0
9
-4
0
1
1
1
ticks
20.0

BUTTON
188
10
254
43
NIL
setup\n
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

BUTTON
261
10
324
43
NIL
go
T
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

SLIDER
7
10
179
43
level
level
0
10
0
1
1
NIL
HORIZONTAL

OUTPUT
7
368
469
669
12

BUTTON
8
327
111
360
show prims
setup\noutput-print ioda:primitives-to-write
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

MONITOR
10
142
120
187
REMAINING
nb-to-create
0
1
11

MONITOR
127
142
213
187
NB TO SAVE
nb-to-save
0
1
11

MONITOR
221
143
294
188
NB SAVED
nb-saved
0
1
11

CHOOSER
330
10
468
55
current-aptitude
current-aptitude
"blocker" "floater"
1

BUTTON
330
62
463
95
choose lemming
select-lemming
T
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

BUTTON
323
101
464
134
NIL
reset-perspective
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

TEXTBOX
13
199
230
236
AVAILABLE APTITUDES
16
0.0
1

MONITOR
11
227
81
272
BLOCKER
nb-aptitudes \"blocker\"
0
1
11

MONITOR
89
227
159
272
FLOATER
nb-aptitudes \"floater\"
0
1
11

BUTTON
329
251
470
284
TERMINATE LEVEL
set quick-end? true
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

BUTTON
307
144
464
177
accelerate lemmings
set creation-period 5 \nset base-speed 1
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

TEXTBOX
20
111
269
151
LEVEL INFORMATION
16
0.0
1

SLIDER
13
61
185
94
base-speed
base-speed
0
1
0.2
0.05
1
NIL
HORIZONTAL

@#$#@#$#@
## WHAT IS IT?

This file is a basic implementation of the "Lemmings" video game (1991) within the IODA NetLogo extension.

## HOW IT WORKS

A cave is initialized in the setup procedure. The main characters are "lemmings" which basically move forward without being aware of any kind of danger. The aim of the player is to lead as many lemmings as possible from the entrance to the exit. Therefore, they can be endowed with several aptitudes, which allow them to exhibit specific behaviors such as blocking the moves of the other lemmings, using parachutes to slow down falls, exploding in order to break walls, etc.


## HOW TO USE IT

You just have to click on **`setup`**, then on **`go`**. Your aim is to endow the characters and the other agents with a better behavior than the initial one.  


## HOW TO CITE

  * The **IODA methodology and simulation algorithms** (i.e. what is actually in use in this NetLogo extension):  
Y. KUBERA, P. MATHIEU and S. PICAULT (2011), "IODA: an interaction-oriented approach for multi-agent based simulations", in: _Journal of Autonomous Agents and Multi-Agent Systems (JAAMAS)_, vol. 23 (3), p. 303-343, Springer DOI: 10.1007/s10458-010-9164-z.  
  * The **key ideas** of the IODA methodology:  
P. MATHIEU and S. PICAULT (2005), "Towards an interaction-based design of behaviors", in: M.-P. Gleizes (ed.), _Proceedings of the The Third European Workshop on Multi-Agent Systems (EUMAS'2005)_.  
  * Do not forget to cite also **NetLogo** itself when you refer to the IODA NetLogo extension:  
U. WILENSKY (1999), NetLogo. http://ccl.northwestern.edu/netlogo Center for Connected Learning and Computer-Based Modeling, Northwestern University. Evanston, IL.

## COPYRIGHT NOTICE

All contents &copy; 2008-2013 Sébastien PICAULT and Philippe MATHIEU  
Laboratoire d'Informatique Fondamentale de Lille (LIFL), UMR CNRS 8021   
University Lille 1 --- Cité Scientifique, F-59655 Villeneuve d'Ascq Cedex, FRANCE.  
Web Site: http://www.lifl.fr/SMAC/projects/ioda

The IODA NetLogo extension is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

IODA NetLogo extension is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with the IODA NetLogo extension. If not, see http://www.gnu.org/licenses.
@#$#@#$#@
default
true
0
Polygon -7500403 true true 150 5 40 250 150 205 260 250

airplane
true
0
Polygon -7500403 true true 150 0 135 15 120 60 120 105 15 165 15 195 120 180 135 240 105 270 120 285 150 270 180 285 210 270 165 240 180 180 285 195 285 165 180 105 180 60 165 15

arrow
true
0
Polygon -7500403 true true 150 0 0 150 105 150 105 293 195 293 195 150 300 150

bee 2
true
0
Polygon -1184463 true false 195 150 105 150 90 165 90 225 105 270 135 300 165 300 195 270 210 225 210 165 195 150
Rectangle -16777216 true false 90 165 212 185
Polygon -16777216 true false 90 207 90 226 210 226 210 207
Polygon -16777216 true false 103 266 198 266 203 246 96 246
Polygon -6459832 true false 120 150 105 135 105 75 120 60 180 60 195 75 195 135 180 150
Polygon -6459832 true false 150 15 120 30 120 60 180 60 180 30
Circle -16777216 true false 105 30 30
Circle -16777216 true false 165 30 30
Polygon -7500403 true true 120 90 75 105 15 90 30 75 120 75
Polygon -16777216 false false 120 75 30 75 15 90 75 105 120 90
Polygon -7500403 true true 180 75 180 90 225 105 285 90 270 75
Polygon -16777216 false false 180 75 270 75 285 90 225 105 180 90
Polygon -7500403 true true 180 75 180 90 195 105 240 195 270 210 285 210 285 150 255 105
Polygon -16777216 false false 180 75 255 105 285 150 285 210 270 210 240 195 195 105 180 90
Polygon -7500403 true true 120 75 45 105 15 150 15 210 30 210 60 195 105 105 120 90
Polygon -16777216 false false 120 75 45 105 15 150 15 210 30 210 60 195 105 105 120 90
Polygon -16777216 true false 135 300 165 300 180 285 120 285

bird
false
0
Polygon -7500403 true true 135 165 90 270 120 300 180 300 210 270 165 165
Rectangle -7500403 true true 120 105 180 237
Polygon -7500403 true true 135 105 120 75 105 45 121 6 167 8 207 25 257 46 180 75 165 105
Circle -16777216 true false 128 21 42
Polygon -7500403 true true 163 116 194 92 212 86 230 86 250 90 265 98 279 111 290 126 296 143 298 158 298 166 296 183 286 204 272 219 259 227 235 240 241 223 250 207 251 192 245 180 232 168 216 162 200 162 186 166 175 173 171 180
Polygon -7500403 true true 137 116 106 92 88 86 70 86 50 90 35 98 21 111 10 126 4 143 2 158 2 166 4 183 14 204 28 219 41 227 65 240 59 223 50 207 49 192 55 180 68 168 84 162 100 162 114 166 125 173 129 180

box
false
0
Polygon -7500403 true true 150 285 285 225 285 75 150 135
Polygon -7500403 true true 150 135 15 75 150 15 285 75
Polygon -7500403 true true 15 75 15 225 150 285 150 135
Line -16777216 false 150 285 150 135
Line -16777216 false 150 135 15 75
Line -16777216 false 150 135 285 75

box 2
false
0
Polygon -7500403 true true 150 285 270 225 270 90 150 150
Polygon -13791810 true false 150 150 30 90 150 30 270 90
Polygon -13345367 true false 30 90 30 225 150 285 150 150

bug
true
0
Circle -7500403 true true 96 182 108
Circle -7500403 true true 110 127 80
Circle -7500403 true true 110 75 80
Line -7500403 true 150 100 80 30
Line -7500403 true 150 100 220 30

butterfly
true
0
Polygon -7500403 true true 150 165 209 199 225 225 225 255 195 270 165 255 150 240
Polygon -7500403 true true 150 165 89 198 75 225 75 255 105 270 135 255 150 240
Polygon -7500403 true true 139 148 100 105 55 90 25 90 10 105 10 135 25 180 40 195 85 194 139 163
Polygon -7500403 true true 162 150 200 105 245 90 275 90 290 105 290 135 275 180 260 195 215 195 162 165
Polygon -16777216 true false 150 255 135 225 120 150 135 120 150 105 165 120 180 150 165 225
Circle -16777216 true false 135 90 30
Line -16777216 false 150 105 195 60
Line -16777216 false 150 105 105 60

car
false
0
Polygon -7500403 true true 300 180 279 164 261 144 240 135 226 132 213 106 203 84 185 63 159 50 135 50 75 60 0 150 0 165 0 225 300 225 300 180
Circle -16777216 true false 180 180 90
Circle -16777216 true false 30 180 90
Polygon -16777216 true false 162 80 132 78 134 135 209 135 194 105 189 96 180 89
Circle -7500403 true true 47 195 58
Circle -7500403 true true 195 195 58

circle
false
0
Circle -7500403 true true 0 0 300

circle 2
false
0
Circle -7500403 true true 0 0 300
Circle -16777216 true false 30 30 240

cloud
false
0
Circle -7500403 true true 13 118 94
Circle -7500403 true true 86 101 127
Circle -7500403 true true 51 51 108
Circle -7500403 true true 118 43 95
Circle -7500403 true true 158 68 134

cow
false
0
Polygon -7500403 true true 200 193 197 249 179 249 177 196 166 187 140 189 93 191 78 179 72 211 49 209 48 181 37 149 25 120 25 89 45 72 103 84 179 75 198 76 252 64 272 81 293 103 285 121 255 121 242 118 224 167
Polygon -7500403 true true 73 210 86 251 62 249 48 208
Polygon -7500403 true true 25 114 16 195 9 204 23 213 25 200 39 123

cylinder
false
0
Circle -7500403 true true 0 0 300

dot
false
0
Circle -7500403 true true 90 90 120

eyes
false
0
Circle -1 true false 62 75 57
Circle -1 true false 182 75 57
Circle -16777216 true false 79 93 20
Circle -16777216 true false 196 93 21

face happy
false
0
Circle -7500403 true true 8 8 285
Circle -16777216 true false 60 75 60
Circle -16777216 true false 180 75 60
Polygon -16777216 true false 150 255 90 239 62 213 47 191 67 179 90 203 109 218 150 225 192 218 210 203 227 181 251 194 236 217 212 240

face neutral
false
0
Circle -7500403 true true 8 7 285
Circle -16777216 true false 60 75 60
Circle -16777216 true false 180 75 60
Rectangle -16777216 true false 60 195 240 225

face sad
false
0
Circle -7500403 true true 8 8 285
Circle -16777216 true false 60 75 60
Circle -16777216 true false 180 75 60
Polygon -16777216 true false 150 168 90 184 62 210 47 232 67 244 90 220 109 205 150 198 192 205 210 220 227 242 251 229 236 206 212 183

fire
false
0
Polygon -7500403 true true 151 286 134 282 103 282 59 248 40 210 32 157 37 108 68 146 71 109 83 72 111 27 127 55 148 11 167 41 180 112 195 57 217 91 226 126 227 203 256 156 256 201 238 263 213 278 183 281
Polygon -955883 true false 126 284 91 251 85 212 91 168 103 132 118 153 125 181 135 141 151 96 185 161 195 203 193 253 164 286
Polygon -2674135 true false 155 284 172 268 172 243 162 224 148 201 130 233 131 260 135 282

fish
false
0
Polygon -1 true false 44 131 21 87 15 86 0 120 15 150 0 180 13 214 20 212 45 166
Polygon -1 true false 135 195 119 235 95 218 76 210 46 204 60 165
Polygon -1 true false 75 45 83 77 71 103 86 114 166 78 135 60
Polygon -7500403 true true 30 136 151 77 226 81 280 119 292 146 292 160 287 170 270 195 195 210 151 212 30 166
Circle -16777216 true false 215 106 30

flag
false
0
Rectangle -7500403 true true 60 15 75 300
Polygon -7500403 true true 90 150 270 90 90 30
Line -7500403 true 75 135 90 135
Line -7500403 true 75 45 90 45

flower
false
0
Polygon -10899396 true false 135 120 165 165 180 210 180 240 150 300 165 300 195 240 195 195 165 135
Circle -7500403 true true 85 132 38
Circle -7500403 true true 130 147 38
Circle -7500403 true true 192 85 38
Circle -7500403 true true 85 40 38
Circle -7500403 true true 177 40 38
Circle -7500403 true true 177 132 38
Circle -7500403 true true 70 85 38
Circle -7500403 true true 130 25 38
Circle -7500403 true true 96 51 108
Circle -16777216 true false 113 68 74
Polygon -10899396 true false 189 233 219 188 249 173 279 188 234 218
Polygon -10899396 true false 180 255 150 210 105 210 75 240 135 240

ghost
false
0
Circle -7500403 true true 61 30 179
Rectangle -7500403 true true 60 120 240 232
Polygon -7500403 true true 60 229 60 284 105 239 149 284 195 240 239 285 239 228 60 229
Circle -1 true false 81 78 56
Circle -16777216 true false 99 98 19
Circle -1 true false 155 80 56
Circle -16777216 true false 171 98 17

house
false
0
Rectangle -7500403 true true 45 120 255 285
Rectangle -16777216 true false 120 210 180 285
Polygon -7500403 true true 15 120 150 15 285 120
Line -16777216 false 30 120 270 120

leaf
false
0
Polygon -7500403 true true 150 210 135 195 120 210 60 210 30 195 60 180 60 165 15 135 30 120 15 105 40 104 45 90 60 90 90 105 105 120 120 120 105 60 120 60 135 30 150 15 165 30 180 60 195 60 180 120 195 120 210 105 240 90 255 90 263 104 285 105 270 120 285 135 240 165 240 180 270 195 240 210 180 210 165 195
Polygon -7500403 true true 135 195 135 240 120 255 105 255 105 285 135 285 165 240 165 195

line
true
0
Line -7500403 true 150 0 150 300

line half
true
0
Line -7500403 true 150 0 150 150

man floating
false
0
Circle -13840069 true false 112 53 75
Rectangle -13840069 true false 136 106 165 225
Polygon -13840069 true false 136 178 90 180 60 120 75 120 105 165 136 164 195 165 225 120 240 120 210 180
Polygon -13840069 true false 136 224 90 255 90 285 60 285 60 300 105 300 105 270 150 240 195 270 195 300 240 300 240 285 210 285 210 255 165 225
Polygon -1 true false 15 120 0 75 15 30 75 0 225 0 285 30 300 75 285 105 255 45 210 30 150 30 75 30 45 45 30 75
Line -1 false 15 90 135 165
Line -1 false 165 165 285 90
Line -1 false 45 45 135 165
Line -1 false 255 45 165 165
Line -1 false 90 30 135 165
Line -1 false 195 30 165 165

man standing
false
0
Circle -13345367 true false 112 53 75
Rectangle -13345367 true false 136 106 165 225
Polygon -13345367 true false 136 178 90 180 60 120 75 120 105 165 136 164 195 165 225 120 240 120 210 180
Polygon -13345367 true false 136 224 90 255 90 285 60 285 60 300 105 300 105 270 150 240 195 270 195 300 240 300 240 285 210 285 210 255 165 225

pacman
true
0
Circle -7500403 true true 0 0 300
Polygon -16777216 true false 105 -15 150 150 195 -15
Circle -16777216 true false 191 101 67

pacman open
true
0
Circle -7500403 true true 0 0 300
Polygon -16777216 true false 270 -15 149 152 30 -15
Circle -16777216 true false 206 101 67

pellet
true
0
Circle -7500403 true true 105 105 92

pentagon
false
0
Polygon -7500403 true true 150 15 15 120 60 285 240 285 285 120

person
false
0
Circle -7500403 true true 110 5 80
Polygon -7500403 true true 105 90 120 195 90 285 105 300 135 300 150 225 165 300 195 300 210 285 180 195 195 90
Rectangle -7500403 true true 127 79 172 94
Polygon -7500403 true true 195 90 240 150 225 180 165 105
Polygon -7500403 true true 105 90 60 150 75 180 135 105

person business
false
0
Rectangle -1 true false 120 90 180 180
Polygon -13345367 true false 135 90 150 105 135 180 150 195 165 180 150 105 165 90
Polygon -7500403 true true 120 90 105 90 60 195 90 210 116 154 120 195 90 285 105 300 135 300 150 225 165 300 195 300 210 285 180 195 183 153 210 210 240 195 195 90 180 90 150 165
Circle -7500403 true true 110 5 80
Rectangle -7500403 true true 127 76 172 91
Line -16777216 false 172 90 161 94
Line -16777216 false 128 90 139 94
Polygon -13345367 true false 195 225 195 300 270 270 270 195
Rectangle -13791810 true false 180 225 195 300
Polygon -14835848 true false 180 226 195 226 270 196 255 196
Polygon -13345367 true false 209 202 209 216 244 202 243 188
Line -16777216 false 180 90 150 165
Line -16777216 false 120 90 150 165

person construction
false
0
Rectangle -7500403 true true 123 76 176 95
Polygon -1 true false 105 90 60 195 90 210 115 162 184 163 210 210 240 195 195 90
Polygon -13345367 true false 180 195 120 195 90 285 105 300 135 300 150 225 165 300 195 300 210 285
Circle -7500403 true true 110 5 80
Line -16777216 false 148 143 150 196
Rectangle -16777216 true false 116 186 182 198
Circle -1 true false 152 143 9
Circle -1 true false 152 166 9
Rectangle -16777216 true false 179 164 183 186
Polygon -955883 true false 180 90 195 90 195 165 195 195 150 195 150 120 180 90
Polygon -955883 true false 120 90 105 90 105 165 105 195 150 195 150 120 120 90
Rectangle -16777216 true false 135 114 150 120
Rectangle -16777216 true false 135 144 150 150
Rectangle -16777216 true false 135 174 150 180
Polygon -955883 true false 105 42 111 16 128 2 149 0 178 6 190 18 192 28 220 29 216 34 201 39 167 35
Polygon -6459832 true false 54 253 54 238 219 73 227 78
Polygon -16777216 true false 15 285 15 255 30 225 45 225 75 255 75 270 45 285

person doctor
false
0
Polygon -7500403 true true 105 90 120 195 90 285 105 300 135 300 150 225 165 300 195 300 210 285 180 195 195 90
Polygon -13345367 true false 135 90 150 105 135 135 150 150 165 135 150 105 165 90
Polygon -7500403 true true 105 90 60 195 90 210 135 105
Polygon -7500403 true true 195 90 240 195 210 210 165 105
Circle -7500403 true true 110 5 80
Rectangle -7500403 true true 127 79 172 94
Polygon -1 true false 105 90 60 195 90 210 114 156 120 195 90 270 210 270 180 195 186 155 210 210 240 195 195 90 165 90 150 150 135 90
Line -16777216 false 150 148 150 270
Line -16777216 false 196 90 151 149
Line -16777216 false 104 90 149 149
Circle -1 true false 180 0 30
Line -16777216 false 180 15 120 15
Line -16777216 false 150 195 165 195
Line -16777216 false 150 240 165 240
Line -16777216 false 150 150 165 150

person farmer
false
0
Polygon -7500403 true true 105 90 120 195 90 285 105 300 135 300 150 225 165 300 195 300 210 285 180 195 195 90
Polygon -1 true false 60 195 90 210 114 154 120 195 180 195 187 157 210 210 240 195 195 90 165 90 150 105 150 150 135 90 105 90
Circle -7500403 true true 110 5 80
Rectangle -7500403 true true 127 79 172 94
Polygon -13345367 true false 120 90 120 180 120 195 90 285 105 300 135 300 150 225 165 300 195 300 210 285 180 195 180 90 172 89 165 135 135 135 127 90
Polygon -6459832 true false 116 4 113 21 71 33 71 40 109 48 117 34 144 27 180 26 188 36 224 23 222 14 178 16 167 0
Line -16777216 false 225 90 270 90
Line -16777216 false 225 15 225 90
Line -16777216 false 270 15 270 90
Line -16777216 false 247 15 247 90
Rectangle -6459832 true false 240 90 255 300

person graduate
false
0
Circle -16777216 false false 39 183 20
Polygon -1 true false 50 203 85 213 118 227 119 207 89 204 52 185
Circle -7500403 true true 110 5 80
Rectangle -7500403 true true 127 79 172 94
Polygon -8630108 true false 90 19 150 37 210 19 195 4 105 4
Polygon -8630108 true false 120 90 105 90 60 195 90 210 120 165 90 285 105 300 195 300 210 285 180 165 210 210 240 195 195 90
Polygon -1184463 true false 135 90 120 90 150 135 180 90 165 90 150 105
Line -2674135 false 195 90 150 135
Line -2674135 false 105 90 150 135
Polygon -1 true false 135 90 150 105 165 90
Circle -1 true false 104 205 20
Circle -1 true false 41 184 20
Circle -16777216 false false 106 206 18
Line -2674135 false 208 22 208 57

person lumberjack
false
0
Polygon -7500403 true true 105 90 120 195 90 285 105 300 135 300 150 225 165 300 195 300 210 285 180 195 195 90
Polygon -2674135 true false 60 196 90 211 114 155 120 196 180 196 187 158 210 211 240 196 195 91 165 91 150 106 150 135 135 91 105 91
Circle -7500403 true true 110 5 80
Rectangle -7500403 true true 127 79 172 94
Polygon -6459832 true false 174 90 181 90 180 195 165 195
Polygon -13345367 true false 180 195 120 195 90 285 105 300 135 300 150 225 165 300 195 300 210 285
Polygon -6459832 true false 126 90 119 90 120 195 135 195
Rectangle -6459832 true false 45 180 255 195
Polygon -16777216 true false 255 165 255 195 240 225 255 240 285 240 300 225 285 195 285 165
Line -16777216 false 135 165 165 165
Line -16777216 false 135 135 165 135
Line -16777216 false 90 135 120 135
Line -16777216 false 105 120 120 120
Line -16777216 false 180 120 195 120
Line -16777216 false 180 135 210 135
Line -16777216 false 90 150 105 165
Line -16777216 false 225 165 210 180
Line -16777216 false 75 165 90 180
Line -16777216 false 210 150 195 165
Line -16777216 false 180 105 210 180
Line -16777216 false 120 105 90 180
Line -16777216 false 150 135 150 165
Polygon -2674135 true false 100 30 104 44 189 24 185 10 173 10 166 1 138 -1 111 3 109 28

person police
false
0
Polygon -1 true false 124 91 150 165 178 91
Polygon -13345367 true false 134 91 149 106 134 181 149 196 164 181 149 106 164 91
Polygon -13345367 true false 180 195 120 195 90 285 105 300 135 300 150 225 165 300 195 300 210 285
Polygon -13345367 true false 120 90 105 90 60 195 90 210 116 158 120 195 180 195 184 158 210 210 240 195 195 90 180 90 165 105 150 165 135 105 120 90
Rectangle -7500403 true true 123 76 176 92
Circle -7500403 true true 110 5 80
Polygon -13345367 true false 150 26 110 41 97 29 137 -1 158 6 185 0 201 6 196 23 204 34 180 33
Line -13345367 false 121 90 194 90
Line -16777216 false 148 143 150 196
Rectangle -16777216 true false 116 186 182 198
Rectangle -16777216 true false 109 183 124 227
Rectangle -16777216 true false 176 183 195 205
Circle -1 true false 152 143 9
Circle -1 true false 152 166 9
Polygon -1184463 true false 172 112 191 112 185 133 179 133
Polygon -1184463 true false 175 6 194 6 189 21 180 21
Line -1184463 false 149 24 197 24
Rectangle -16777216 true false 101 177 122 187
Rectangle -16777216 true false 179 164 183 186

person service
false
0
Polygon -7500403 true true 180 195 120 195 90 285 105 300 135 300 150 225 165 300 195 300 210 285
Polygon -1 true false 120 90 105 90 60 195 90 210 120 150 120 195 180 195 180 150 210 210 240 195 195 90 180 90 165 105 150 165 135 105 120 90
Polygon -1 true false 123 90 149 141 177 90
Rectangle -7500403 true true 123 76 176 92
Circle -7500403 true true 110 5 80
Line -13345367 false 121 90 194 90
Line -16777216 false 148 143 150 196
Rectangle -16777216 true false 116 186 182 198
Circle -1 true false 152 143 9
Circle -1 true false 152 166 9
Rectangle -16777216 true false 179 164 183 186
Polygon -2674135 true false 180 90 195 90 183 160 180 195 150 195 150 135 180 90
Polygon -2674135 true false 120 90 105 90 114 161 120 195 150 195 150 135 120 90
Polygon -2674135 true false 155 91 128 77 128 101
Rectangle -16777216 true false 118 129 141 140
Polygon -2674135 true false 145 91 172 77 172 101

person soldier
false
0
Rectangle -7500403 true true 127 79 172 94
Polygon -10899396 true false 105 90 60 195 90 210 135 105
Polygon -10899396 true false 195 90 240 195 210 210 165 105
Circle -7500403 true true 110 5 80
Polygon -10899396 true false 105 90 120 195 90 285 105 300 135 300 150 225 165 300 195 300 210 285 180 195 195 90
Polygon -6459832 true false 120 90 105 90 180 195 180 165
Line -6459832 false 109 105 139 105
Line -6459832 false 122 125 151 117
Line -6459832 false 137 143 159 134
Line -6459832 false 158 179 181 158
Line -6459832 false 146 160 169 146
Rectangle -6459832 true false 120 193 180 201
Polygon -6459832 true false 122 4 107 16 102 39 105 53 148 34 192 27 189 17 172 2 145 0
Polygon -16777216 true false 183 90 240 15 247 22 193 90
Rectangle -6459832 true false 114 187 128 208
Rectangle -6459832 true false 177 187 191 208

person student
false
0
Polygon -13791810 true false 135 90 150 105 135 165 150 180 165 165 150 105 165 90
Polygon -7500403 true true 195 90 240 195 210 210 165 105
Circle -7500403 true true 110 5 80
Rectangle -7500403 true true 127 79 172 94
Polygon -7500403 true true 105 90 120 195 90 285 105 300 135 300 150 225 165 300 195 300 210 285 180 195 195 90
Polygon -1 true false 100 210 130 225 145 165 85 135 63 189
Polygon -13791810 true false 90 210 120 225 135 165 67 130 53 189
Polygon -1 true false 120 224 131 225 124 210
Line -16777216 false 139 168 126 225
Line -16777216 false 140 167 76 136
Polygon -7500403 true true 105 90 60 195 90 210 135 105

person-1
false
0
Polygon -7500403 true true 120 195 120 105 135 90 195 90 195 105 195 120 180 180
Circle -7500403 true true 125 5 80
Rectangle -7500403 true true 147 73 183 90
Polygon -7500403 true true 165 195 120 180 96 228 60 255 75 300 133 242
Polygon -7500403 true true 180 180 120 180 156 232 159 299 199 299 195 223
Polygon -7500403 true true 135 90 95 125 60 180 90 195 127 137 150 120
Polygon -7500403 true true 180 136 180 151 206 202 234 190 210 136 195 91

person-2
false
0
Polygon -7500403 true true 180 136 165 166 192 203 220 191 202 147 191 98
Circle -7500403 true true 125 5 80
Rectangle -7500403 true true 147 73 183 90
Polygon -7500403 true true 168 176 117 160 105 225 75 255 90 300 135 255
Polygon -7500403 true true 174 164 120 180 150 225 150 300 195 300 195 225
Polygon -7500403 true true 105 180 120 105 135 90 187 89 195 105 195 120 180 180
Polygon -7500403 true true 131 91 96 135 75 180 120 195 135 150 150 120

person-3
false
0
Polygon -7500403 true true 171 123 181 162 165 200 196 201 198 150 194 104
Polygon -7500403 true true 165 180 120 180 120 225 90 285 135 300 165 225
Polygon -7500403 true true 180 180 121 180 158 239 135 300 180 300 180 225
Circle -7500403 true true 125 5 80
Rectangle -7500403 true true 147 73 183 90
Polygon -7500403 true true 116 192 120 105 135 90 180 90 195 105 195 120 180 195
Polygon -7500403 true true 135 90 120 105 93 193 128 199 135 150 150 120

person-4
false
0
Polygon -7500403 true true 180 135 180 165 175 205 186 203 190 135 183 89
Circle -7500403 true true 125 5 80
Rectangle -7500403 true true 147 73 183 90
Polygon -7500403 true true 165 180 120 180 120 225 120 300 165 300 165 225
Polygon -7500403 true true 181 182 151 182 136 227 136 302 181 302 181 212
Polygon -7500403 true true 120 190 120 105 130 90 180 90 195 105 195 120 180 195
Polygon -7500403 true true 145 90 130 102 120 135 120 165 120 210 150 210

person-5
false
0
Circle -7500403 true true 125 5 80
Rectangle -7500403 true true 147 73 183 90
Polygon -7500403 true true 180 180 135 180 135 225 120 300 180 300 180 225
Polygon -7500403 true true 195 180 120 180 135 225 150 300 195 300 201 222
Polygon -7500403 true true 120 195 120 105 135 90 180 90 195 105 195 120 180 195
Polygon -7500403 true true 180 135 180 165 180 195 195 195 195 120 183 89
Polygon -7500403 true true 136 128 136 158 136 203 166 203 165 120 142 95

person-6
false
0
Polygon -7500403 true true 165 180 120 180 122 222 105 300 150 300 165 225
Polygon -7500403 true true 182 174 120 178 165 240 173 298 214 294 210 225
Circle -7500403 true true 125 5 80
Rectangle -7500403 true true 147 73 183 90
Polygon -7500403 true true 116 192 120 105 135 90 180 90 195 105 195 120 180 195
Polygon -7500403 true true 135 90 105 120 93 193 120 195 120 135 150 120
Polygon -7500403 true true 150 124 150 154 161 201 195 199 193 133 183 93

person-7
false
0
Circle -7500403 true true 125 5 80
Rectangle -7500403 true true 147 73 183 90
Polygon -7500403 true true 165 180 120 180 105 240 90 300 135 300 150 240
Polygon -7500403 true true 165 166 120 181 177 229 180 297 221 289 213 210
Polygon -7500403 true true 120 195 120 105 135 90 180 90 195 105 195 120 189 193
Polygon -7500403 true true 122 103 100 133 79 195 111 202 124 165 154 120
Polygon -7500403 true true 180 133 165 163 180 210 210 195 198 146 195 105

person-8
false
0
Polygon -7500403 true true 183 168 138 183 178 239 203 300 240 280 213 226
Circle -7500403 true true 125 5 80
Rectangle -7500403 true true 147 73 183 90
Polygon -7500403 true true 120 180 120 105 135 90 180 90 195 105 195 120 195 180
Polygon -7500403 true true 135 90 99 127 66 183 92 197 118 158 150 120
Polygon -7500403 true true 180 133 165 163 210 195 232 179 203 141 183 87
Polygon -7500403 true true 165 180 120 180 105 240 75 300 120 300 150 240

person-9
false
0
Circle -7500403 true true 125 5 80
Rectangle -7500403 true true 147 73 183 90
Polygon -7500403 true true 165 180 120 180 105 225 68 289 105 300 150 225
Polygon -7500403 true true 188 185 135 180 163 240 171 300 217 301 200 229
Polygon -7500403 true true 121 180 121 105 136 90 181 90 195 97 196 120 187 185
Polygon -7500403 true true 135 90 90 135 60 180 90 195 120 150 150 120
Polygon -7500403 true true 185 137 185 154 222 199 246 179 215 139 185 92

personr-1
false
0
Polygon -7500403 true true 180 195 180 105 165 90 105 90 105 105 105 120 120 180
Circle -7500403 true true 95 5 80
Rectangle -7500403 true true 117 73 153 90
Polygon -7500403 true true 135 195 180 180 204 228 240 255 225 300 167 242
Polygon -7500403 true true 120 180 180 180 144 232 141 299 101 299 105 223
Polygon -7500403 true true 165 90 205 125 240 180 210 195 173 137 150 120
Polygon -7500403 true true 120 136 120 151 94 202 66 190 90 136 105 91

personr-2
false
0
Polygon -7500403 true true 120 136 135 166 108 203 80 191 98 147 109 98
Circle -7500403 true true 95 5 80
Rectangle -7500403 true true 117 73 153 90
Polygon -7500403 true true 132 176 183 160 195 225 225 255 210 300 165 255
Polygon -7500403 true true 126 164 180 180 150 225 150 300 105 300 105 225
Polygon -7500403 true true 195 180 180 105 165 90 113 89 105 105 105 120 120 180
Polygon -7500403 true true 169 91 204 135 225 180 180 195 165 150 150 120

personr-3
false
0
Polygon -7500403 true true 129 123 119 162 135 200 104 201 102 150 106 104
Polygon -7500403 true true 135 180 180 180 180 225 210 285 165 300 135 225
Polygon -7500403 true true 120 180 179 180 142 239 165 300 120 300 120 225
Circle -7500403 true true 95 5 80
Rectangle -7500403 true true 117 73 153 90
Polygon -7500403 true true 184 192 180 105 165 90 120 90 105 105 105 120 120 195
Polygon -7500403 true true 165 90 180 105 207 193 172 199 165 150 150 120

personr-4
false
0
Polygon -7500403 true true 120 135 120 165 125 205 114 203 110 135 117 89
Circle -7500403 true true 95 5 80
Rectangle -7500403 true true 117 73 153 90
Polygon -7500403 true true 135 180 180 180 180 225 180 300 135 300 135 225
Polygon -7500403 true true 119 182 149 182 164 227 164 302 119 302 119 212
Polygon -7500403 true true 180 190 180 105 170 90 120 90 105 105 105 120 120 195
Polygon -7500403 true true 155 90 170 102 180 135 180 165 180 210 150 210

personr-5
false
0
Circle -7500403 true true 95 5 80
Rectangle -7500403 true true 117 73 153 90
Polygon -7500403 true true 120 180 165 180 165 225 180 300 120 300 120 225
Polygon -7500403 true true 105 180 180 180 165 225 150 300 105 300 99 222
Polygon -7500403 true true 180 195 180 105 165 90 120 90 105 105 105 120 120 195
Polygon -7500403 true true 120 135 120 165 120 195 105 195 105 120 117 89
Polygon -7500403 true true 164 128 164 158 164 203 134 203 135 120 158 95

personr-6
false
0
Polygon -7500403 true true 135 180 180 180 178 222 195 300 150 300 135 225
Polygon -7500403 true true 118 174 180 178 135 240 127 298 86 294 90 225
Circle -7500403 true true 95 5 80
Rectangle -7500403 true true 117 73 153 90
Polygon -7500403 true true 184 192 180 105 165 90 120 90 105 105 105 120 120 195
Polygon -7500403 true true 165 90 195 120 207 193 180 195 180 135 150 120
Polygon -7500403 true true 150 124 150 154 139 201 105 199 107 133 117 93

personr-7
false
0
Circle -7500403 true true 95 5 80
Rectangle -7500403 true true 117 73 153 90
Polygon -7500403 true true 135 180 180 180 195 240 210 300 165 300 150 240
Polygon -7500403 true true 135 166 180 181 123 229 120 297 79 289 87 210
Polygon -7500403 true true 180 195 180 105 165 90 120 90 105 105 105 120 111 193
Polygon -7500403 true true 178 103 200 133 221 195 189 202 176 165 146 120
Polygon -7500403 true true 120 133 135 163 120 210 90 195 102 146 105 105

personr-8
false
0
Polygon -7500403 true true 117 168 162 183 122 239 97 300 60 280 87 226
Circle -7500403 true true 95 5 80
Rectangle -7500403 true true 117 73 153 90
Polygon -7500403 true true 180 180 180 105 165 90 120 90 105 105 105 120 105 180
Polygon -7500403 true true 165 90 201 127 234 183 208 197 182 158 150 120
Polygon -7500403 true true 120 133 135 163 90 195 68 179 97 141 117 87
Polygon -7500403 true true 135 180 180 180 195 240 225 300 180 300 150 240

personr-9
false
0
Circle -7500403 true true 95 5 80
Rectangle -7500403 true true 117 73 153 90
Polygon -7500403 true true 135 180 180 180 195 225 232 289 195 300 150 225
Polygon -7500403 true true 112 185 165 180 137 240 129 300 83 301 100 229
Polygon -7500403 true true 179 180 179 105 164 90 119 90 105 97 104 120 113 185
Polygon -7500403 true true 165 90 210 135 240 180 210 195 180 150 150 120
Polygon -7500403 true true 115 137 115 154 78 199 54 179 85 139 115 92

plant
false
0
Rectangle -7500403 true true 135 90 165 300
Polygon -7500403 true true 135 255 90 210 45 195 75 255 135 285
Polygon -7500403 true true 165 255 210 210 255 195 225 255 165 285
Polygon -7500403 true true 135 180 90 135 45 120 75 180 135 210
Polygon -7500403 true true 165 180 165 210 225 180 255 120 210 135
Polygon -7500403 true true 135 105 90 60 45 45 75 105 135 135
Polygon -7500403 true true 165 105 165 135 225 105 255 45 210 60
Polygon -7500403 true true 135 90 120 45 150 15 180 45 165 90

rock
false
0
Circle -7500403 true true 13 118 94
Circle -7500403 true true 176 176 127
Circle -7500403 true true 171 21 108
Circle -7500403 true true 28 43 95
Circle -7500403 true true 173 68 134
Circle -7500403 true true 53 173 134
Circle -7500403 true true 78 48 175

scared
false
0
Circle -13345367 true false 61 30 179
Rectangle -13345367 true false 60 120 240 232
Polygon -13345367 true false 60 229 60 284 105 239 149 284 195 240 239 285 239 228 60 229
Circle -16777216 true false 81 78 56
Circle -16777216 true false 155 80 56
Line -16777216 false 137 193 102 166
Line -16777216 false 103 166 75 194
Line -16777216 false 138 193 171 165
Line -16777216 false 172 166 198 192

sheep
false
0
Rectangle -7500403 true true 151 225 180 285
Rectangle -7500403 true true 47 225 75 285
Rectangle -7500403 true true 15 75 210 225
Circle -7500403 true true 135 75 150
Circle -16777216 true false 165 76 116

splash
false
15
Polygon -1 true true 147 119 96 57 55 64 29 69 68 71 110 87
Polygon -1 true true 115 134 56 137 28 166 71 149
Polygon -1 true true 195 131 249 93 271 125 235 128
Polygon -1 true true 169 171 222 187 250 251 215 196
Polygon -1 true true 134 161 78 205 85 260 98 206
Circle -1 true true 111 110 72
Polygon -1 true true 148 110 195 127 207 154 153 183 101 159 111 132

square
false
0
Rectangle -7500403 true true 30 30 270 270

square 2
false
0
Rectangle -7500403 true true 30 30 270 270
Rectangle -16777216 true false 60 60 240 240

star
false
0
Polygon -7500403 true true 151 1 185 108 298 108 207 175 242 282 151 216 59 282 94 175 3 108 116 108

sun
false
0
Circle -7500403 true true 75 75 150
Polygon -7500403 true true 300 150 240 120 240 180
Polygon -7500403 true true 150 0 120 60 180 60
Polygon -7500403 true true 150 300 120 240 180 240
Polygon -7500403 true true 0 150 60 120 60 180
Polygon -7500403 true true 60 195 105 240 45 255
Polygon -7500403 true true 60 105 105 60 45 45
Polygon -7500403 true true 195 60 240 105 255 45
Polygon -7500403 true true 240 195 195 240 255 255

target
false
0
Circle -7500403 true true 0 0 300
Circle -16777216 true false 30 30 240
Circle -7500403 true true 60 60 180
Circle -16777216 true false 90 90 120
Circle -7500403 true true 120 120 60

tile brick
false
0
Rectangle -1 true false 0 0 300 300
Rectangle -7500403 true true 15 225 150 285
Rectangle -7500403 true true 165 225 300 285
Rectangle -7500403 true true 75 150 210 210
Rectangle -7500403 true true 0 150 60 210
Rectangle -7500403 true true 225 150 300 210
Rectangle -7500403 true true 165 75 300 135
Rectangle -7500403 true true 15 75 150 135
Rectangle -7500403 true true 0 0 60 60
Rectangle -7500403 true true 225 0 300 60
Rectangle -7500403 true true 75 0 210 60

tile log
false
0
Rectangle -7500403 true true 0 0 300 300
Line -16777216 false 0 30 45 15
Line -16777216 false 45 15 120 30
Line -16777216 false 120 30 180 45
Line -16777216 false 180 45 225 45
Line -16777216 false 225 45 165 60
Line -16777216 false 165 60 120 75
Line -16777216 false 120 75 30 60
Line -16777216 false 30 60 0 60
Line -16777216 false 300 30 270 45
Line -16777216 false 270 45 255 60
Line -16777216 false 255 60 300 60
Polygon -16777216 false false 15 120 90 90 136 95 210 75 270 90 300 120 270 150 195 165 150 150 60 150 30 135
Polygon -16777216 false false 63 134 166 135 230 142 270 120 210 105 116 120 88 122
Polygon -16777216 false false 22 45 84 53 144 49 50 31
Line -16777216 false 0 180 15 180
Line -16777216 false 15 180 105 195
Line -16777216 false 105 195 180 195
Line -16777216 false 225 210 165 225
Line -16777216 false 165 225 60 225
Line -16777216 false 60 225 0 210
Line -16777216 false 300 180 264 191
Line -16777216 false 255 225 300 210
Line -16777216 false 16 196 116 211
Line -16777216 false 180 300 105 285
Line -16777216 false 135 255 240 240
Line -16777216 false 240 240 300 255
Line -16777216 false 135 255 105 285
Line -16777216 false 180 0 240 15
Line -16777216 false 240 15 300 0
Line -16777216 false 0 300 45 285
Line -16777216 false 45 285 45 270
Line -16777216 false 45 270 0 255
Polygon -16777216 false false 150 270 225 300 300 285 228 264
Line -16777216 false 223 209 255 225
Line -16777216 false 179 196 227 183
Line -16777216 false 228 183 266 192

tile stones
false
0
Polygon -7500403 true true 0 240 45 195 75 180 90 165 90 135 45 120 0 135
Polygon -7500403 true true 300 240 285 210 270 180 270 150 300 135 300 225
Polygon -7500403 true true 225 300 240 270 270 255 285 255 300 285 300 300
Polygon -7500403 true true 0 285 30 300 0 300
Polygon -7500403 true true 225 0 210 15 210 30 255 60 285 45 300 30 300 0
Polygon -7500403 true true 0 30 30 0 0 0
Polygon -7500403 true true 15 30 75 0 180 0 195 30 225 60 210 90 135 60 45 60
Polygon -7500403 true true 0 105 30 105 75 120 105 105 90 75 45 75 0 60
Polygon -7500403 true true 300 60 240 75 255 105 285 120 300 105
Polygon -7500403 true true 120 75 120 105 105 135 105 165 165 150 240 150 255 135 240 105 210 105 180 90 150 75
Polygon -7500403 true true 75 300 135 285 195 300
Polygon -7500403 true true 30 285 75 285 120 270 150 270 150 210 90 195 60 210 15 255
Polygon -7500403 true true 180 285 240 255 255 225 255 195 240 165 195 165 150 165 135 195 165 210 165 255

tile water
false
0
Rectangle -7500403 true true -1 0 299 300
Polygon -1 true false 105 259 180 290 212 299 168 271 103 255 32 221 1 216 35 234
Polygon -1 true false 300 161 248 127 195 107 245 141 300 167
Polygon -1 true false 0 157 45 181 79 194 45 166 0 151
Polygon -1 true false 179 42 105 12 60 0 120 30 180 45 254 77 299 93 254 63
Polygon -1 true false 99 91 50 71 0 57 51 81 165 135
Polygon -1 true false 194 224 258 254 295 261 211 221 144 199

tree
false
0
Circle -7500403 true true 118 3 94
Rectangle -6459832 true false 120 195 180 300
Circle -7500403 true true 65 21 108
Circle -7500403 true true 116 41 127
Circle -7500403 true true 45 90 120
Circle -7500403 true true 104 74 152

triangle
false
0
Polygon -7500403 true true 150 30 15 255 285 255

triangle 2
false
0
Polygon -7500403 true true 150 30 15 255 285 255
Polygon -16777216 true false 151 99 225 223 75 224

truck
false
0
Rectangle -7500403 true true 4 45 195 187
Polygon -7500403 true true 296 193 296 150 259 134 244 104 208 104 207 194
Rectangle -1 true false 195 60 195 105
Polygon -16777216 true false 238 112 252 141 219 141 218 112
Circle -16777216 true false 234 174 42
Rectangle -7500403 true true 181 185 214 194
Circle -16777216 true false 144 174 42
Circle -16777216 true false 24 174 42
Circle -7500403 false true 24 174 42
Circle -7500403 false true 144 174 42
Circle -7500403 false true 234 174 42

turtle
true
0
Polygon -10899396 true false 215 204 240 233 246 254 228 266 215 252 193 210
Polygon -10899396 true false 195 90 225 75 245 75 260 89 269 108 261 124 240 105 225 105 210 105
Polygon -10899396 true false 105 90 75 75 55 75 40 89 31 108 39 124 60 105 75 105 90 105
Polygon -10899396 true false 132 85 134 64 107 51 108 17 150 2 192 18 192 52 169 65 172 87
Polygon -10899396 true false 85 204 60 233 54 254 72 266 85 252 107 210
Polygon -7500403 true true 119 75 179 75 209 101 224 135 220 225 175 261 128 261 81 224 74 135 88 99

wheel
false
0
Circle -7500403 true true 3 3 294
Circle -16777216 true false 30 30 240
Line -7500403 true 150 285 150 15
Line -7500403 true 15 150 285 150
Circle -7500403 true true 120 120 60
Line -7500403 true 216 40 79 269
Line -7500403 true 40 84 269 221
Line -7500403 true 40 216 269 79
Line -7500403 true 84 40 221 269

x
false
0
Polygon -7500403 true true 270 75 225 30 30 225 75 270
Polygon -7500403 true true 30 75 75 30 270 225 225 270

@#$#@#$#@
NetLogo 5.0.4
@#$#@#$#@
@#$#@#$#@
@#$#@#$#@
<experiments>
  <experiment name="experiment" repetitions="20" runMetricsEveryStep="false">
    <setup>setup</setup>
    <go>go</go>
    <timeLimit steps="5000"/>
    <metric>score</metric>
    <metric>remaining-bees</metric>
    <enumeratedValueSet variable="nb-walls">
      <value value="20"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="nb-bees">
      <value value="0"/>
      <value value="1"/>
      <value value="2"/>
      <value value="3"/>
      <value value="4"/>
      <value value="5"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="pengi-halo">
      <value value="5"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="nb-ice">
      <value value="50"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="nb-fish">
      <value value="20"/>
    </enumeratedValueSet>
  </experiment>
</experiments>
@#$#@#$#@
@#$#@#$#@
default
0.0
-0.2 0 1.0 0.0
0.0 1 1.0 0.0
0.2 0 1.0 0.0
link direction
true
0
Line -7500403 true 150 150 90 180
Line -7500403 true 150 150 210 180

@#$#@#$#@
0
@#$#@#$#@
