# Proposal: The Kinematic Term “Centrivical” for Inward Swirling Vector Fields

**Status:** proposed, not established. This document is a companion summary of the canonical manuscript in [`book.md`](book.md). It claims no priority, no new force, and no dynamical law.

## 1. Rationale and scope

Inward swirling motion appears in many fluid systems: drain vortices, low-level cyclone inflow, accretion disks, and stretched vortex tubes such as the Burgers vortex. Each combines motion **toward** a reference axis with motion **around** that axis.

Existing terms separate or blur these features:

| Term | Usual meaning | Distinction from the proposed term |
|---|---|---|
| Centripetal | Inward radial acceleration or force; for uniform circular motion $\mathbf a_c=-\Omega^2 r\,\mathbf e_r$ | Does not require inward velocity or nonzero azimuthal velocity |
| Centrifugal | Outward fictitious force in a rotating frame, $\mathbf F_{\mathrm{cf}}=m\Omega^2 r\,\mathbf e_r$ | Opposite radial direction; no swirl requirement |
| Swirling | Nonzero azimuthal velocity, $u_\phi\neq 0$ | No inward-drift requirement |
| Vortical | Often: nonzero vorticity, $\nabla\times\mathbf u\neq\mathbf 0$ | Vorticity is a vector/pseudovector field, not azimuthal velocity |
| **Centrivical** (proposed) | $u_r<0$ and $u_\phi\neq 0$ | Inward drift and swirl together |

The phrase “inward-spiraling flow” already works. The proposed adjective offers compactness, not necessity. It is a sign-constraining kinematic label, weaker than differential conditions such as solenoidality or irrotationality. It carries no force law, acceleration requirement, or conservation principle.

## 2. Geometry and core predicate

Let $E$ be an oriented Euclidean 3-space with a chosen oriented axis $A$. Let $\mathbf e_z$ point along the positive direction of $A$. For $x\notin A$, define

$$
r(x)=\operatorname{dist}(x,A)>0,
\qquad
\mathbf e_\phi=\mathbf e_z\times\mathbf e_r,
$$

where $\mathbf e_r$ points radially away from $A$. Any vector field decomposes as

$$
\mathbf u=u_r\,\mathbf e_r+u_\phi\,\mathbf e_\phi+u_z\,\mathbf e_z.
$$

> **Definition (centrivical).** A vector field $\mathbf u$ is **centrivical at $x$ about $A$** iff
> $$u_r(x)<0 \quad\text{and}\quad u_\phi(x)\neq 0.$$
> It is **centrivical on a set $D$** iff these inequalities hold at every point of $D$.

Scope:

1. The predicate is pointwise and does not require axisymmetry.
2. It is relative to a stated axis and observer frame.
3. It is undefined on the axis, where $\mathbf e_r$ and $\mathbf e_\phi$ are undefined. The field itself need not be singular there.
4. For time-dependent fields, evaluate at a fixed time or on a stated space-time set.

### Pointwise swirl is not circulation

Circulation is the loop integral

$$
\Gamma(C)=\oint_C \mathbf u\cdot d\boldsymbol\ell.
$$

A nonzero $u_\phi$ at one point does not determine $\Gamma(C)$. Conversely, on $r>0$ the field

$$
u_r=u_z=0,\qquad
u_\phi=\frac{\Gamma}{2\pi r}(1-\cos\phi)
$$

has circulation $\Gamma$ around every positively oriented loop linking $A$ once, yet $u_\phi=0$ on the ray $\phi=0$. In this proposal, “circulation” always means the loop integral; the pointwise condition is called nonzero azimuthal velocity or nonzero swirl.

## 3. Conventions and assumptions

These are conventions and regularity assumptions, not physical axioms.

- **A0 — Proposed status.** “Centrivical” has no meaning independent of the definition above.
- **A1 — Geometry and frame.** The axis $A$, its orientation, and the observer frame are fixed in advance.
- **A2 — Set.** $D\subset E\setminus A$ is nonempty and need not be connected.
- **A3 — Regularity.** Continuity suffices for the predicate. Derivative statements need corresponding differentiability. Flow and $\omega$-limit statements assume $D$ is open and the autonomous field is locally Lipschitz.
- **A4 — Definition.** The field is centrivical on $D$ iff $u_r<0$ and $u_\phi\neq 0$ at every point of $D$.
- **A5 — Flow convention.** Trajectories satisfy $\dot x=\mathbf u(x,t)$. For autonomous fields,
  $$
  \dot r=u_r,\qquad \dot\phi=\frac{u_\phi}{r},\qquad \dot z=u_z.
  $$
- **A6 — No force language.** The definition defines no “centrivical force” and no force law. A force field with these component signs must be described separately and must not be identified with centripetal force.

## 4. Symmetry, scaling, superposition, and frames

- **L1 — Axis-preserving isometries.** Translations along $A$ and rotations about $A$ preserve centrivicality. A reflection in a plane containing $A$ flips the sign of $u_\phi$ but preserves $u_\phi\neq 0$.
- **L2 — Axial irrelevance.** For any scalar function $f$, $\mathbf u$ is centrivical iff $\mathbf u+f\,\mathbf e_z$ is.
- **L3 — Time reversal.** Reversing a velocity field makes $u_r>0$, so the reversed field is not centrivical.
- **L4 — Scaling.** For constant $\lambda>0$, $\lambda\mathbf u$ is centrivical iff $\mathbf u$ is. If $\mathbf u$ is centrivical and $\lambda\le 0$, then $\lambda\mathbf u$ is not.
- **L5 — Non-closure under addition.** Radial parts add to something negative, but azimuthal parts can cancel:
  $$
  (-\mathbf e_r+\mathbf e_\phi)+(-\mathbf e_r-\mathbf e_\phi)=-2\mathbf e_r.
  $$
  Centrivical fields therefore do not form a vector space. Fields with a fixed swirl sign are closed under addition and positive scaling.
- **L6 — Galilean boosts.** Under a boost by constant transverse velocity $\mathbf V$,
  $$
  u_r'=u_r-\mathbf V\cdot\mathbf e_r,\qquad
  u_\phi'=u_\phi-\mathbf V\cdot\mathbf e_\phi.
  $$
  Either inequality can be created or destroyed. Name the observer frame together with the axis.
- **L7 — Rotating frames.** In a frame rotating at constant rate $\Omega$ about $A$,
  $$
  u_r'=u_r,\qquad u_\phi'=u_\phi-\Omega r.
  $$
  Inflow survives every co-axial rotating frame, but swirl can be cancelled exactly.

## 5. Trajectories

- **L8a — Radial monotonicity.** Along an autonomous trajectory remaining where $u_r<0$, $\dot r<0$. Hence $r$ strictly decreases and no periodic orbit lies wholly in $D$. This uses inflow alone.
- **L8b — Angular motion.** For a centrivical autonomous flow, $\dot\phi=u_\phi/r\neq 0$. An unwrapped angle is strictly monotone, so the transverse projection moves inward with monotone angular motion. The full trajectory may also move axially. On each connected component of $D$, the sign of $u_\phi$ is constant. Monotone angular motion need not complete infinitely many revolutions before the axis or a boundary is reached.
- **L8c — Radial limit.** Let a trajectory remain in $D$ on its maximal forward interval $[0,T_{\max})$. Since $r(t)>0$ decreases strictly,
  $$
  r_\infty=\lim_{t\uparrow T_{\max}} r(t)
  $$
  exists and is nonnegative. If $r_\infty=0$, the trajectory approaches the axis in distance. If $r_\infty>0$, no general spatial fate follows: the trajectory may approach a non-axis boundary, escape axially to infinity, or have no finite spatial limit.

Counterexample to any longer list: on $D=\{r>1\}$,

$$
\mathbf u=-(r-1)\,\mathbf e_r+\mathbf e_\phi
$$

is centrivical and satisfies

$$
r(t)=1+(r(0)-1)e^{-t}.
$$

Trajectories approach the inner/non-axis boundary $r=1$, never the axis. Under A3, a forward-complete trajectory has no $\omega$-limit point inside $D$; boundary limit points are not excluded.

- **L9 — Streamline equation.** Wherever $u_\phi\neq 0$,
  $$
  \frac{dr}{d\phi}=\frac{r\,u_r}{u_\phi}.
  $$

## 6. Independence from divergence and vorticity

In cylindrical coordinates,

$$
\nabla\cdot\mathbf u=
\frac1r\frac{\partial(r u_r)}{\partial r}
+\frac1r\frac{\partial u_\phi}{\partial\phi}
+\frac{\partial u_z}{\partial z}.
$$

The pointwise inequalities constrain none of these derivatives locally. For example, if the azimuthal and axial derivative terms vanish, then $\partial u_r/\partial r=-u_r/r$ gives zero divergence, $\partial u_r/\partial r<-u_r/r$ gives negative divergence, and $\partial u_r/\partial r>-u_r/r$ gives positive divergence. Global fields still obey integral identities and boundary-flux constraints.

The curl is

$$
\nabla\times\mathbf u=
\left(\frac1r\frac{\partial u_z}{\partial\phi}-\frac{\partial u_\phi}{\partial z}\right)\mathbf e_r
+\left(\frac{\partial u_r}{\partial z}-\frac{\partial u_z}{\partial r}\right)\mathbf e_\phi
+\left(\frac1r\frac{\partial(r u_\phi)}{\partial r}-\frac1r\frac{\partial u_r}{\partial\phi}\right)\mathbf e_z.
$$

Centrivical fields may be irrotational away from the axis or rotational. The predicate implies no particular local vorticity sign or magnitude; it does not license arbitrary global vorticity profiles.

- **L12 — Excluded cases.** Pure rotation ($u_r=0$), pure radial inflow ($u_\phi=0$), and outward motion ($u_r>0$) are not centrivical.
- **P1 — Projection characterization.** A field is centrivical on $D$ exactly when its projection onto the plane normal to $A$ has negative radial and nonzero azimuthal component throughout $D$.
- **P2 — No dynamics.** Centrivicality does not imply centripetal acceleration, incompressibility, any particular vorticity, a force law, or a conservation law.

## 7. Canonical examples

1. **Linear inward spiral.**
   $$
   \mathbf u=-a r\,\mathbf e_r+\Omega r\,\mathbf e_\phi,\qquad a>0,\ \Omega\neq 0.
   $$
   Divergence $-2a$; vorticity $2\Omega\,\mathbf e_z$; streamlines
   $r(\phi)=r_0\exp[-(a/\Omega)(\phi-\phi_0)]$.

2. **Sink plus vortex.**
   $$
   \mathbf u=-\frac{Q}{2\pi r}\,\mathbf e_r+\frac{\Gamma}{2\pi r}\,\mathbf e_\phi,\qquad Q>0,\ \Gamma\neq 0.
   $$
   Incompressible and curl-free away from the singular axis; nonzero circulation $\Gamma$; streamlines
   $r(\phi)=r_0\exp[-(Q/\Gamma)(\phi-\phi_0)]$.

3. **Burgers vortex.**
   $$
   u_r=-\frac a2 r,\qquad
   u_\phi=\frac{\Gamma}{2\pi r}\left(1-e^{-a r^2/(4\nu)}\right),\qquad
   u_z=a z,
   $$
   with $a>0$, $\nu>0$, $\Gamma\neq 0$. Incompressible, rotational, smooth through the axis, and centrivical for $r>0$.

4. **Boundary approach.**
   $$
   \mathbf u=-(r-1)\,\mathbf e_r+\mathbf e_\phi
   $$
   on $r>1$. Centrivical, yet trajectories approach $r=1$ rather than the axis.

| Example | $u_r$ | $u_\phi$ | $u_z$ | $\nabla\cdot\mathbf u$ | $(\nabla\times\mathbf u)_z$ | Domain | Radial limit |
|---|---|---|---|---|---|---|---|
| Linear spiral | $-ar$ | $\Omega r$ | $0$ | $-2a$ | $2\Omega$ | $r>0$ | $0$ |
| Sink + vortex | $-Q/(2\pi r)$ | $\Gamma/(2\pi r)$ | $0$ | $0$ | $0$ | $r>0$ | $0$ |
| Burgers vortex | $-(a/2)r$ | $\frac{\Gamma}{2\pi r}(1-e^{-ar^2/(4\nu)})$ | $az$ | $0$ | $\frac{a\Gamma}{4\pi\nu}e^{-ar^2/(4\nu)}$ | $r>0$ | $0$ |
| Boundary approach | $-(r-1)$ | $1$ | $0$ | $-(2r-1)/r$ | $1/r$ | $r>1$ | $1$ |

## 8. Measurement and uncertainty

For compact $D_0\subset D$ and $\varepsilon>0$, call $\mathbf u$ **$(\varepsilon,D_0)$-centrivical** if

$$
u_r\le -\varepsilon,\qquad |u_\phi|\ge\varepsilon
\quad\text{on }D_0.
$$

This is a margin, not a tolerance. A continuous strictly centrivical field on a compact set has some positive margin.

For closed component intervals $I_r$ and $I_\phi$ that are guaranteed enclosures in the stated axis and frame:

- **centrivical** if $\sup I_r<0$ and $0\notin I_\phi$;
- **not centrivical** if $I_r\subset[0,\infty)$ or $I_\phi=\{0\}$;
- **undetermined** otherwise.

If the intervals are probabilistic confidence intervals, the verdicts inherit that uncertainty and should not be called certified. Failing to resolve swirl above a noise floor is not evidence of zero swirl.

For a centrivical field define the signed transverse angle

$$
\beta=\operatorname{atan2}(u_\phi,-u_r)
\in\left(-\frac\pi2,\frac\pi2\right)\setminus\{0\}.
$$

Then $\operatorname{sgn}\beta=\operatorname{sgn}u_\phi$. Positive values mean counterclockwise swirl viewed from the positive end of $A$. The angle ignores $u_z$ and determines neither a swirl number nor helicity.

## 9. Recommended usage

The term is proposed, so these are conventions rather than mandates:

1. State the definition at first use.
2. Prefer “centrivical flow” and “centrivical field”; avoid “centrivical force.”
3. Name the axis and observer frame together.
4. Avoid contexts where readers may mistake the word for a third classical force.
5. Use *centrivicality* and *centrivically* if useful; avoid the bare noun “a centrivical.”
6. Do not infer incompressibility, vorticity, centripetal acceleration, or a force law.
7. Reserve “circulation” for the loop integral.
8. Keep margin diagnostics separate from the strict definition and report “undetermined” when uncertainty prevents a decision.

## 10. Conclusion

“Centrivical” is proposed as a narrow kinematic adjective: inward radial motion plus nonzero azimuthal motion, relative to a stated axis and frame. It is invariant under axis-preserving isometries and axial additions, but not under time reversal, Galilean boosts, co-axial rotating frames, or arbitrary superposition. It is locally independent of divergence and vorticity. Inflow alone rules out periodic orbits; swirl adds monotone angular motion. The only general radial conclusion is that $r(t)$ decreases to a limit.

Whether the word is worth adopting is for its potential users to decide. This proposal aims only to make the term precise enough to be judged.
