package org.centrivical.explorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { App() } }
    }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun App() {
    var tab by rememberSaveable { mutableIntStateOf(0) }
    val titles = listOf("Define", "Classify", "Explore")
    Scaffold(bottomBar = {
        NavigationBar {
            titles.forEachIndexed { i, t ->
                NavigationBarItem(
                    selected = tab == i,
                    onClick = { tab = i },
                    icon = { Text(t.take(1), fontWeight = FontWeight.Bold) },
                    label = { Text(t) }
                )
            }
        }
    }) { pad ->
        Box(Modifier.padding(pad).fillMaxSize()) {
            when (tab) {
                0 -> DefineScreen()
                1 -> ClassifyScreen()
                else -> ExploreScreen()
            }
        }
    }
}

@Composable
fun DefineScreen() {
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Centrivical", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Status: a PROPOSED term, not an established term in mathematics or physics.",
            fontWeight = FontWeight.Bold
        )
        Text(
            "Definition. A field u is centrivical at a point x (off the axis A) iff\n\n" +
                "    u_r(x) < 0   and   u_φ(x) ≠ 0,\n\n" +
                "i.e. inward radial drift plus nonzero azimuthal velocity, relative to a stated axis and frame."
        )
        Text(
            "• Not a force: no 'centrivical force', no force law.\n" +
                "• Not circulation: circulation is the loop integral ∮ u·dl.\n" +
                "• Not vorticity: centrivical fields may be irrotational or rotational.\n" +
                "• Frame-dependent: boosts and rotating frames can change the verdict.\n" +
                "• Inflow alone rules out periodic orbits within the region; swirl adds monotone angular motion."
        )
        Text("Etymology: a blend of centripetal + vortical ('center-seeking swirl').")
        Text("Source and book: https://github.com/artistso/Centrivical", style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun NumberField(label: String, value: String, onChange: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        singleLine = true,
        modifier = modifier
    )
}

@Composable
fun ClassifyScreen() {
    var ur by rememberSaveable { mutableStateOf("-1.0") }
    var dR by rememberSaveable { mutableStateOf("0.1") }
    var up by rememberSaveable { mutableStateOf("0.5") }
    var dPhi by rememberSaveable { mutableStateOf("0.1") }

    val a = ur.toDoubleOrNull()
    val da = dR.toDoubleOrNull()
    val b = up.toDoubleOrNull()
    val db = dPhi.toDoubleOrNull()
    val intervals = if (a != null && da != null && b != null && db != null) {
        Centrivical.measurementIntervals(a, da, b, db)
    } else null

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Measurement classifier", style = MaterialTheme.typography.titleLarge)
        Text("Enter estimates with ± uncertainty. Intervals must be finite guaranteed enclosures in the stated axis and frame.")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NumberField("u_r", ur, { ur = it }, Modifier.weight(1f))
            NumberField("± δr", dR, { dR = it }, Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NumberField("u_φ", up, { up = it }, Modifier.weight(1f))
            NumberField("± δφ", dPhi, { dPhi = it }, Modifier.weight(1f))
        }
        if (intervals == null) {
            Text(
                "Enter finite numbers; uncertainties must be ≥ 0 and interval endpoints must not overflow.",
                color = Color(0xFFC62828)
            )
        } else {
            val (ir, iphi) = intervals
            val verdict = Centrivical.classify(ir, iphi)
            val (txt, col) = when (verdict) {
                Verdict.CENTRIVICAL -> "CENTRIVICAL (enclosure-certified)" to Color(0xFF2E7D32)
                Verdict.NOT_CENTRIVICAL -> "NOT CENTRIVICAL (enclosure-certified)" to Color(0xFFC62828)
                Verdict.UNDETERMINED -> "UNDETERMINED" to Color(0xFFEF6C00)
            }
            Text(txt, color = col, style = MaterialTheme.typography.headlineSmall)
            Text("I_r = [${fmt(ir.lo)}, ${fmt(ir.hi)}],   I_φ = [${fmt(iphi.lo)}, ${fmt(iphi.hi)}]")
            val beta = Centrivical.beta(a!!, b!!)
            Text(
                if (beta != null)
                    "Central values: β = ${fmt(Math.toDegrees(beta))}°  (${if (beta > 0) "counterclockwise" else "clockwise"} viewed from +A)"
                else "Central values are not centrivical; β undefined."
            )
            Text(
                "Rule: centrivical if sup I_r < 0 and 0 ∉ I_φ; not centrivical if I_r ⊂ [0,∞) or I_φ = {0}; " +
                    "otherwise undetermined. Unresolved swirl is not evidence of zero swirl. Probabilistic confidence intervals do not certify a verdict.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

fun fmt(x: Double) = String.format(Locale.US, "%.3f", x)

private data class ExploreData(
    val lines: List<List<Pair<Double, Double>>>,
    val sampledCentrivical: Boolean,
    val sampleCount: Int
)

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen() {
    var which by rememberSaveable { mutableIntStateOf(0) }
    var k by rememberSaveable { mutableFloatStateOf(1f) }
    var s by rememberSaveable { mutableFloatStateOf(1f) }
    val rMax = 2.0

    val field = remember(which, k, s) { makeField(which, k.toDouble(), s.toDouble()) }
    val rLo = field.rMin + 0.05
    val data by produceState<ExploreData?>(initialValue = null, key1 = field) {
        value = withContext(Dispatchers.Default) {
            val rings = if (field.rMin > 0) listOf(1.9, 1.4) else listOf(1.9, 1.0)
            val seeds = rings.flatMap { r0 ->
                (0 until 8).map { j ->
                    val p = 2 * PI * j / 8
                    r0 * cos(p) to r0 * sin(p)
                }
            }
            val lines = seeds.map { streamline(field, it.first, it.second, rMax) }
            val ok = centrivicalOnSampledAnnulus(field, rLo, rMax)
            ExploreData(lines, ok, 41 * 36)
        }
    }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            exampleNames.forEachIndexed { i, n ->
                FilterChip(selected = which == i, onClick = { which = i }, label = { Text(n) })
            }
        }
        Text(field.formula, style = MaterialTheme.typography.bodyMedium)
        Text("Examples use a fixed laboratory frame about +z. Inflow k = ${String.format(Locale.US, "%.1f", k)}")
        Slider(value = k, onValueChange = { k = it }, valueRange = -2f..2f, steps = 39)
        Text("Swirl s = ${String.format(Locale.US, "%.1f", s)}")
        Slider(value = s, onValueChange = { s = it }, valueRange = -2f..2f, steps = 39)
        val current = data
        if (current == null) {
            CircularProgressIndicator()
            Text("Computing streamlines…", style = MaterialTheme.typography.bodySmall)
        } else {
            Text(
                if (current.sampledCentrivical)
                    "Sampled check on ${fmt(rLo)} ≤ r ≤ 2: centrivical at all ${current.sampleCount} sample points."
                else
                    "Sampled check on ${fmt(rLo)} ≤ r ≤ 2: FAILS at some sample point (not centrivical on the sampled annulus).",
                color = if (current.sampledCentrivical) Color(0xFF2E7D32) else Color(0xFFC62828)
            )
        }
        Text("Sampling illustrates; it does not prove.", style = MaterialTheme.typography.bodySmall)

        Canvas(
            Modifier.fillMaxWidth().aspectRatio(1f).semantics {
                contentDescription = "Streamline plot in the transverse plane, viewed from the positive axis. " +
                    if (current?.sampledCentrivical == true) "Sampled annulus is centrivical." else "Sampled annulus is not centrivical or still computing."
            }
        ) {
            val scale = (size.minDimension / (2 * rMax)).toFloat()
            val c = Offset(size.width / 2, size.height / 2)
            fun toScreen(x: Double, y: Double) = Offset(c.x + (x * scale).toFloat(), c.y - (y * scale).toFloat())
            drawCircle(Color.LightGray, radius = (rMax * scale).toFloat(), center = c, style = Stroke(1.5f))
            if (field.rMin > 0) drawCircle(Color.Gray, radius = (field.rMin * scale).toFloat(), center = c, style = Stroke(3f))
            current?.lines?.forEach { pts ->
                if (pts.size > 1) {
                    val path = Path()
                    val p0 = toScreen(pts[0].first, pts[0].second)
                    path.moveTo(p0.x, p0.y)
                    for (i in 1 until pts.size) {
                        val p = toScreen(pts[i].first, pts[i].second)
                        path.lineTo(p.x, p.y)
                    }
                    drawPath(path, Color(0xFF1565C0), style = Stroke(3f))
                }
                if (pts.isNotEmpty()) {
                    drawCircle(Color(0xFF1565C0), radius = 5f, center = toScreen(pts[0].first, pts[0].second))
                }
            }
            drawCircle(Color.Black, radius = 6f, center = c) // axis A, viewed from +A
        }
        Text(
            "Black dot: axis A (viewed from its positive end). Dots: seed points. " +
                "Gray circle (Boundary approach): r = 1, which trajectories approach instead of the axis (L8c).",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
