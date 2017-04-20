var math = Java.type("java.lang.Math")
var sin = math.sin
var cos = math.cos
var tan = math.tan
var asin = math.asin
var acos = math.acos
var atan = math.atan
var sinh = math.sinh
var cosh = math.cosh
var tanh = math.tanh
var atan2 = math.atan2
var hypot = math.hypot
var ln = math.log
var log = math.log10
var sqrt = math.sqrt
var π = math.PI
var e = math.E
var abs = math.abs
var exp = math.exp
var pow = math.pow
var cbrt = math.cbrt
var ceil = math.ceil
var round = math.round
var floor = math.floor
var rad = math.toRadians
var deg = math.toDegrees
var signum = math.signum
var rand = function(max, min){return (math.random()*max)+min};
var mod = function(x, y){return (x - y * math.floor(x / y))};
var sec = function(x){return 1/math.cos(x)};
var csc = function(x){return 1/math.sin(x)};
var cot = function(x){return 1/math.tan(x)};
var lerp = function(start, stop, amt) {return start + (stop - start) * amt};
var norm = function(value, start, stop) {return (value - start) / (stop - start)};
var clamp = function(val, min, max){return math.min(math.min(val, min), max)};
var map = function(value, sMin, sMax, dMin, dMax) {return dMin + (dMax - dMin) * ((value - sMin) / (sMax - sMin))};