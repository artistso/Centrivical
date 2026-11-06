---
title: "Centrivical"
subtitle: "A Proposed Kinematic Term for Inward Swirling Flow"
author: "Centrivical contributors"
date: "2026"
lang: en-US
rights: "© 2026 Centrivical contributors. Text licensed under Creative Commons Attribution 4.0 (CC BY 4.0)."
documentclass: book
classoption: oneside
geometry: "paperwidth=6in, paperheight=9in, margin=0.85in"
fontsize: 11pt
toc: true
---

# Preface {-}

This short book proposes a word. It does not report a discovery, a force, or a law of nature. **"Centrivical" is a proposed term, not an established term in mathematics or physics**, and nothing here claims standard usage or priority.

The proposal is narrow. A field or flow is *centrivical* about an axis when it moves inward toward that axis while also moving around it. That is all. The rest of the book explains why the definition is stated the way it is. It tests the definition against symmetries, frame changes, superposition and trajectory behavior, and it records what the word deliberately does *not* claim.

The text grew out of several rounds of critical review. Each round removed an overstatement: circulation was not the right word, inflow alone rules out closed orbits, and the asymptotic fates of trajectories cannot be fully classified. The corrections are built into the definitions and lemmas that follow.

The companion Android app source, *Centrivical Explorer*, implements the definition, the measurement classifier of Chapter 9 and the examples of Chapter 8 (see Appendix C).

Comments and objections are welcome via the repository issue tracker: https://github.com/artistso/Centrivical/issues.

# Why Propose a Word?

Inward swirling motion is common: water entering a drain, air spiraling into the low-level inflow of a tropical cyclone, gas drifting inward in an accretion disk, the stretched vortex tubes of turbulence modeled by the Burgers vortex. Each combines two features: motion **toward** an axis and motion **around** it.

Existing vocabulary splits these features or blurs them:

- *Centripetal* describes an inward **acceleration** or force, not an inward velocity, and requires no swirl.
- *Centrifugal* describes an outward fictitious force in a rotating frame.
- *Vortical* is used in fluid mechanics for nonzero **vorticity**, which is a different property from swirling velocity.
- *Swirling* says nothing about inward drift.

The phrase "inward-spiraling flow" already works, and nothing in this book shows that a new word is *necessary*. The proposal offers compactness: a single adjective with a precise, checkable definition, usable in sentences such as "the flow is centrivical about $A$ in the laboratory frame on the annulus $1<r<2$."

The word carries **no dynamical content**. It is a kinematic label, like "irrotational" or "solenoidal," but weaker. It constrains signs, not equations.

# The Definition

## Geometry

Work in an oriented Euclidean 3-space $E$ with a chosen oriented axis $A$. Let $\mathbf e_z$ be the unit vector along the positive direction of $A$. At a point $x\notin A$, let $r(x)=\operatorname{dist}(x,A)>0$, let $\mathbf e_r$ point radially away from $A$, and set
$$
\mathbf e_\phi=\mathbf e_z\times\mathbf e_r .
$$
Any vector field decomposes as
$$
\mathbf u=u_r\,\mathbf e_r+u_\phi\,\mathbf e_\phi+u_z\,\mathbf e_z .
$$

## The predicate

> **Definition (centrivical).** A vector field $\mathbf u$ is **centrivical at $x$ about $A$** iff
> $$u_r(x)<0 \quad\text{and}\quad u_\phi(x)\neq 0.$$
> It is **centrivical on a set $D$** iff these inequalities hold at every point of $D$.

For a velocity field this reads: **inward radial drift plus nonzero azimuthal velocity** (nonzero swirl).

Four remarks fix the scope:

1. The definition is **pointwise**. It does not require axisymmetry.
2. It is **relative to a stated axis and frame**. Change either and the verdict may change (Chapter 5).
3. It is **undefined on the axis**, where $\mathbf e_r$ and $\mathbf e_\phi$ are undefined. The field itself need not be singular there.
4. For a **time-dependent** field, apply it at a fixed time or pointwise on a stated space-time set.

## Circulation is a different thing

The predicate does **not** assert nonzero circulation. Circulation is a loop integral,
$$
\Gamma(C)=\oint_C \mathbf u\cdot d\boldsymbol\ell ,
$$
which depends on the field all along the loop. A nonzero $u_\phi$ at one point says nothing about it.

Conversely, nonzero circulation does not require $u_\phi\neq 0$ everywhere. On $r>0$ the field
$$
u_r=u_z=0,\qquad u_\phi=\frac{\Gamma}{2\pi r}(1-\cos\phi)
$$
has circulation $\Gamma$ around every positively oriented loop linking $A$ once. Yet $u_\phi=0$ on the ray $\phi=0$.

In this book, **"circulation" always means $\Gamma(C)$**. The pointwise condition is always called "nonzero azimuthal velocity" or "nonzero swirl."

# Neighboring Terms

| Term | Meaning | Distinction from "centrivical" |
|---|---|---|
| Centripetal | Inward radial acceleration or force; for uniform circular motion $\mathbf a_c=-\Omega^2 r\,\mathbf e_r$ | Requires neither inward velocity nor swirl |
| Centrifugal | Outward fictitious force in a rotating frame, $\mathbf F_{\mathrm{cf}}=m\Omega^2 r\,\mathbf e_r$ | Opposite radial direction; no swirl required |
| Swirling | $u_\phi\neq 0$ | No inward drift required |
| Vortical | Often: nonzero vorticity $\nabla\times\mathbf u$ | Vorticity is not azimuthal velocity |
| **Centrivical** (proposed) | $u_r<0$ and $u_\phi\neq 0$ | Inward drift and swirl together |

A centrivical field is not thereby a centripetal force, a centrifugal force, or a field with nonzero vorticity. The sink-plus-vortex flow of Chapter 8 swirls and is centrivical, yet it is irrotational away from the axis.

# Conventions and Assumptions

All later statements are conditional on these. They are definitions, conventions and regularity assumptions, not physical laws.

**A0 — Proposed status.** "Centrivical" has no meaning independent of the definition in Chapter 2.

**A1 — Geometry and frame.** The axis $A$, its orientation and the frame in which the field is given are fixed in advance.

**A2 — Set.** $D\subset E\setminus A$ is nonempty and need not be connected.

**A3 — Regularity.** Continuity suffices for the predicate. Derivative statements need corresponding differentiability. Flow and $\omega$-limit statements assume $D$ is open and the autonomous field is locally Lipschitz.

**A4 — Definition.** The field is centrivical on $D$ iff $u_r<0$ and $u_\phi\neq 0$ at every point of $D$.

**A5 — Flow convention.** Trajectories satisfy $\dot x=\mathbf u(x,t)$. For autonomous fields,
$$
\dot r=u_r,\qquad \dot\phi=\frac{u_\phi}{r},\qquad \dot z=u_z .
$$

**A6 — No force language.** The definition defines no "centrivical force" and no force law. A force field with these component signs must be described separately and explicitly. It must never be identified with centripetal force.

# Symmetry, Scaling, Superposition and Frames

**L1 — Axis-preserving isometries.** Translations along $A$ and rotations about $A$ preserve centrivicality. A reflection in a plane containing $A$ flips the sign of $u_\phi$ but keeps $u_\phi\neq 0$.

**L2 — Axial irrelevance.** For any scalar function $f$, $\mathbf u$ is centrivical iff $\mathbf u+f\,\mathbf e_z$ is.

**L3 — Time reversal.** Reversing a velocity field makes $u_r>0$, so the reversed field is not centrivical.

**L4 — Scaling.** For a constant $\lambda>0$, $\lambda\mathbf u$ is centrivical iff $\mathbf u$ is. If $\mathbf u$ is centrivical and $\lambda\le 0$, then $\lambda\mathbf u$ is not: negative scaling reverses the radial sign, and zero scaling makes it zero.

**L5 — Non-closure under addition.** Radial parts of two centrivical fields add to something negative, but azimuthal parts can cancel:
$$
(-\mathbf e_r+\mathbf e_\phi)+(-\mathbf e_r-\mathbf e_\phi)=-2\,\mathbf e_r .
$$
Centrivical fields therefore do not form a vector space, and centrivicality is not preserved by arbitrary superposition. (Fields with a *fixed* swirl sign do behave better; see Exercise 5.)

**L6 — Galilean boosts.** Under a boost by constant velocity $\mathbf V$ transverse to $A$,
$$
u_r'=u_r-\mathbf V\cdot\mathbf e_r,\qquad u_\phi'=u_\phi-\mathbf V\cdot\mathbf e_\phi .
$$
Either inequality can be created or destroyed. **Always name the observer frame in the same sentence as the axis.**

**L7 — Rotating frames.** In a frame rotating at constant rate $\Omega$ about $A$,
$$
u_r'=u_r,\qquad u_\phi'=u_\phi-\Omega r .
$$
Inflow survives every co-axial rotating frame, but swirl can be cancelled exactly.

# Trajectories

**L8a — Radial monotonicity.** Along an autonomous trajectory remaining in a region where $u_r<0$, $\dot r=u_r<0$. So $r$ strictly decreases and **no periodic orbit lies wholly in $D$**. This uses inflow alone; swirl plays no role.

**L8b — Angular motion.** For a centrivical autonomous flow, $\dot\phi=u_\phi/r\neq 0$. An unwrapped angle is strictly monotone along each trajectory, so the transverse projection spirals inward. The full trajectory may also move axially, because $u_z$ is unrestricted. On each **connected component** of $D$, the sign of $u_\phi$ is constant. Different components may swirl in opposite senses. Monotone angular motion need not complete infinitely many revolutions before the axis or a boundary is reached.

**L8c — The radial limit, and its limits.** Let a trajectory remain in $D$ on its maximal forward interval $[0,T_{\max})$. Since $r(t)>0$ decreases strictly, the limit
$$
r_\infty=\lim_{t\uparrow T_{\max}} r(t)
$$
exists and is nonnegative.

- If $r_\infty=0$, the trajectory approaches the axis in distance: in finite time if $T_{\max}<\infty$, asymptotically otherwise.
- If $r_\infty>0$, **nothing general follows**. The trajectory may approach a non-axis boundary, escape to infinity along the axis, or have no finite spatial limit.

*Counterexample to any longer list.* On $D=\{r>1\}$ the field $\mathbf u=-(r-1)\,\mathbf e_r+\mathbf e_\phi$ is centrivical. Its trajectories satisfy
$$
r(t)=1+(r(0)-1)\,e^{-t}.
$$
They approach the boundary circle $r=1$, never the axis.

What *can* be said: under the assumptions of A3, a forward-complete trajectory has no $\omega$-limit point inside $D$. At such a point $u_r<0$ would force a further decrease of $r$, inconsistent with recurrence. Limit points on the boundary are not excluded.

**L9 — Streamline equation.** Wherever $u_\phi\neq 0$,
$$
\frac{dr}{d\phi}=\frac{r\,u_r}{u_\phi}.
$$

# What the Word Does Not Say

**L10 — Divergence is independent.** In cylindrical coordinates,
$$
\nabla\cdot\mathbf u=\frac1r\frac{\partial(r u_r)}{\partial r}+\frac1r\frac{\partial u_\phi}{\partial\phi}+\frac{\partial u_z}{\partial z},
$$
and centrivicality constrains none of it pointwise. Global fields still obey the usual integral identities and boundary-flux constraints.

**L11 — Vorticity is independent.** A centrivical field may be irrotational away from the axis (Example 2 below) or rotational (Example 3).

**L12 — Excluded cases.** Pure rotation ($u_r=0$), pure radial inflow ($u_\phi=0$) and outward motion ($u_r>0$) are not centrivical.

**P1 — Characterization.** A field is centrivical on $D$ exactly when its projection onto the plane normal to $A$ has negative radial and nonzero azimuthal component throughout $D$.

**P2 — No dynamics.** Centrivicality does not imply centripetal acceleration, incompressibility, any particular vorticity, a force law or a conservation law.

# Canonical Examples

**Example 1 — Linear inward spiral.**
$$
\mathbf u=-a r\,\mathbf e_r+\Omega r\,\mathbf e_\phi,\qquad a>0,\ \Omega\neq 0 .
$$
Here $r(t)=r_0e^{-at}$ and $r(\phi)=r_0\exp[-(a/\Omega)(\phi-\phi_0)]$: a logarithmic spiral.

**Example 2 — Sink plus vortex.**
$$
\mathbf u=-\frac{Q}{2\pi r}\,\mathbf e_r+\frac{\Gamma}{2\pi r}\,\mathbf e_\phi,\qquad Q>0,\ \Gamma\neq 0 .
$$
$Q$ is the sink strength and $\Gamma$ the circulation. The flow is incompressible and curl-free away from the axis, singular on it, and has streamlines $r(\phi)=r_0\exp[-(Q/\Gamma)(\phi-\phi_0)]$. Its nonzero circulation despite zero curl comes from the topology of the punctured domain.

**Example 3 — Burgers vortex.**
$$
u_r=-\frac a2 r,\qquad u_\phi=\frac{\Gamma}{2\pi r}\left(1-e^{-a r^2/(4\nu)}\right),\qquad u_z=a z,
$$
with $a>0$, $\nu>0$, $\Gamma\neq 0$. It is incompressible, rotational and smooth through the axis. It is centrivical for $r>0$.

**Example 4 — Boundary approach (from L8c).** $\mathbf u=-(r-1)\,\mathbf e_r+\mathbf e_\phi$ on $r>1$. It is centrivical, yet trajectories approach $r=1$ rather than the axis.

Together these separate the predicate from divergence and from vorticity, and they show the limits of trajectory classification.

# From Definition to Data

## Uniform margin

For a compact $D_0\subset D$ and $\varepsilon>0$, call $\mathbf u$ **$(\varepsilon,D_0)$-centrivical** if
$$
u_r\le -\varepsilon,\qquad |u_\phi|\ge\varepsilon \quad\text{on } D_0 .
$$
This is a **margin**, not a tolerance. A continuous, strictly centrivical field on a compact set automatically has some positive margin. An isolated zero of $u_\phi$ fails both the strict definition and every margin condition.

## Measurement uncertainty

Suppose component estimates come with closed uncertainty intervals $I_r$ and $I_\phi$ that are guaranteed enclosures of the true components in the stated axis and frame. If the intervals are merely probabilistic confidence intervals, the verdicts inherit that uncertainty and should not be described as certified. The result is:

- **centrivical** if $\sup I_r<0$ and $0\notin I_\phi$;
- **not centrivical** if $I_r\subset[0,\infty)$ or $I_\phi=\{0\}$;
- **undetermined** otherwise.

Failing to resolve swirl above a noise floor is not evidence that swirl is zero.

## Signed transverse angle

For a centrivical field define
$$
\beta=\operatorname{atan2}(u_\phi,-u_r)\in\left(-\tfrac\pi2,\tfrac\pi2\right)\setminus\{0\}.
$$
Then $\operatorname{sgn}\beta=\operatorname{sgn}u_\phi$. Positive values mean counterclockwise swirl viewed from the positive end of $A$. $|\beta|$ is the angle between the transverse velocity and the inward radial direction. $\beta$ ignores $u_z$, and centrivicality alone determines neither a swirl number nor helicity.

# Usage Guide

**Etymology.** The word is a deliberate blend of *centripetal* and *vortical*, meaning loosely "center-seeking swirl." It is not a regular Latin formation: *centripetal* comes from *centrum* and *petere*, and *vortical* from *vortex*. **Centrovortical** would be a more transparent compound. This proposal retains *centrivical*.

**Recommended conventions.**

1. State the definition at first use: "By *centrivical* I mean a field with inward radial motion and nonzero azimuthal motion about a stated axis."
2. Prefer "centrivical flow" and "centrivical field." Avoid "centrivical force."
3. Name the axis and the observer frame together.
4. Do not place the word where readers may take it for a third classical force beside "centripetal" and "centrifugal."
5. Noun: *centrivicality*. Adverb: *centrivically*. Avoid the bare noun "a centrivical."
6. Do not infer incompressibility, vorticity, centripetal acceleration or any force law.
7. Reserve "circulation" for the loop integral.
8. Keep the margin diagnostic separate from the strict definition. Report "undetermined" when uncertainty prevents a decision.

# Conclusion

"Centrivical" is proposed as a narrow kinematic adjective: inward radial motion plus nonzero azimuthal motion, relative to a stated axis and frame.

- It is invariant under axis-preserving isometries and axial additions.
- It is not invariant under time reversal, Galilean boosts, co-axial rotating frames or arbitrary superposition.
- It is independent of divergence and vorticity.
- Inflow alone rules out periodic orbits, and swirl adds angular motion.
- The only general radial conclusion is that $r(t)$ decreases to a limit.

Whether the word is worth adopting is for its potential users to decide. This book aims only to make the proposal precise enough to be judged.

# Appendix A — Notation {-}

| Symbol | Meaning |
|---|---|
| $A$ | Oriented reference axis |
| $r,\phi,z$ | Cylindrical coordinates about $A$ |
| $u_r,u_\phi,u_z$ | Radial, azimuthal and axial components |
| $D$ | Set on which centrivicality is asserted ($D\cap A=\varnothing$) |
| $\Gamma(C)$ | Circulation around a closed curve $C$ |
| $\beta$ | Signed transverse angle |
| $I_r,I_\phi$ | Uncertainty intervals for measured components |

# Appendix B — Exercises with Solutions {-}

**1.** Is $\mathbf u=-\mathbf e_r+5\,\mathbf e_z$ centrivical?
*Solution.* No. $u_\phi=0$; this is pure radial inflow with axial motion.

**2.** Is $\mathbf u=-r\,\mathbf e_r+(1-\cos\phi)\,\mathbf e_\phi$ centrivical on $r>0$?
*Solution.* No. It fails on the ray $\phi=0$, although it is centrivical on the complement of that ray.

**3.** Example 1 with rate $\Omega$ is viewed from a frame rotating at the same rate $\Omega$ about $A$. Is it centrivical there?
*Solution.* No. By L7, $u_\phi'=\Omega r-\Omega r=0$ everywhere; the flow appears as pure radial inflow.

**4.** Compute $\beta$ for Example 1.
*Solution.* $\beta=\operatorname{atan2}(\Omega r, a r)=\arctan(\Omega/a)$. This is constant, which is why the streamlines are logarithmic spirals.

**5.** Let $\mathbf u,\mathbf v$ be centrivical on $D$ with $u_\phi>0$ and $v_\phi>0$. Show that $\mathbf u+\mathbf v$ is centrivical.
*Solution.* $u_r+v_r<0$ and $u_\phi+v_\phi>0$. Fields with a fixed positive swirl sign are therefore closed under addition and under positive scaling. L5 fails only when swirl signs differ.

**6.** Measured $u_r=-0.2\pm0.3$ and $u_\phi=1.0\pm0.1$. Classify.
*Solution.* $I_r=[-0.5,0.1]$ contains values $\ge 0$ and values $<0$, so the result is **undetermined**.

# Appendix C — Companion App {-}

*Centrivical Explorer* is a companion Android app with three screens. Its source lives in `app/` and its unit tests in `app/src/test/`:

- **Define**: the definition and its status.
- **Classify**: enter measured components with uncertainties. The app returns *centrivical*, *not centrivical* or *undetermined*, plus $\beta$.
- **Explore**: interactive streamlines for Examples 1–4, with sliders for inflow and swirl and a sampled centrivicality check.

The sampled check in the app is a numerical illustration, not a proof.