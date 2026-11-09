---
name: Neiva Fleet & Rental Operations
colors:
  surface: '#faf8ff'
  surface-dim: '#d2d9f4'
  surface-bright: '#faf8ff'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f2f3ff'
  surface-container: '#eaedff'
  surface-container-high: '#e2e7ff'
  surface-container-highest: '#dae2fd'
  on-surface: '#131b2e'
  on-surface-variant: '#434655'
  inverse-surface: '#283044'
  inverse-on-surface: '#eef0ff'
  outline: '#747686'
  outline-variant: '#c4c5d7'
  surface-tint: '#2151da'
  primary: '#0037b0'
  on-primary: '#ffffff'
  primary-container: '#1d4ed8'
  on-primary-container: '#cad3ff'
  inverse-primary: '#b7c4ff'
  secondary: '#4059aa'
  on-secondary: '#ffffff'
  secondary-container: '#8fa7fe'
  on-secondary-container: '#1d3989'
  tertiary: '#374655'
  on-tertiary: '#ffffff'
  tertiary-container: '#4f5d6e'
  on-tertiary-container: '#c7d6e9'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#dce1ff'
  primary-fixed-dim: '#b7c4ff'
  on-primary-fixed: '#001551'
  on-primary-fixed-variant: '#0039b5'
  secondary-fixed: '#dce1ff'
  secondary-fixed-dim: '#b6c4ff'
  on-secondary-fixed: '#00164e'
  on-secondary-fixed-variant: '#264191'
  tertiary-fixed: '#d5e4f8'
  tertiary-fixed-dim: '#b9c8db'
  on-tertiary-fixed: '#0e1d2b'
  on-tertiary-fixed-variant: '#3a4858'
  background: '#faf8ff'
  on-background: '#131b2e'
  surface-variant: '#dae2fd'
typography:
  display-lg:
    fontFamily: Inter
    fontSize: 32px
    fontWeight: '700'
    lineHeight: 40px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Inter
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 32px
    letterSpacing: -0.015em
  headline-md:
    fontFamily: Inter
    fontSize: 20px
    fontWeight: '600'
    lineHeight: 28px
    letterSpacing: -0.01em
  headline-sm:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '600'
    lineHeight: 24px
    letterSpacing: 0em
  body-lg:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
    letterSpacing: 0em
  body-md:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
    letterSpacing: 0em
  body-sm:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '400'
    lineHeight: 16px
    letterSpacing: 0em
  label-lg:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '500'
    lineHeight: 20px
    letterSpacing: 0.01em
  label-md:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '600'
    lineHeight: 16px
    letterSpacing: 0.02em
  label-sm:
    fontFamily: Inter
    fontSize: 11px
    fontWeight: '600'
    lineHeight: 14px
    letterSpacing: 0.03em
  currency-display:
    fontFamily: Inter
    fontSize: 20px
    fontWeight: '700'
    lineHeight: 26px
    letterSpacing: -0.01em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 1.5rem
  margin: 2rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 1rem
  space-lg: 1.5rem
  space-xl: 2rem
---

## Brand & Style
This design system defines an enterprise-grade car rental and fleet management platform tailored for vehicle agencies operating in Neiva, Colombia. The aesthetic balances corporate rigor with modern operational velocity, utilizing clean data displays, dense information architecture, and immediate visual hierarchy to support front-desk agents, fleet managers, and dispatch operators.

The core visual language relies on a refined Corporate Modern approach: high-efficiency white and cool-tinted workspaces, crisp delineations, high-contrast readable typography, and strict semantic states. The interface prioritizes clarity, error prevention, and rapid status assessment over decorative flourishes. Photography is strictly avoided; UI focus is anchored by structured line iconography, vehicular silhouettes, and explicit monetary data in Colombian Pesos (COP).

## Colors
The color foundation is engineered for extended daytime desktop use, maximizing contrast without visual fatigue.

- **Primary Canvas & Surfaces**: The base application canvas uses ultra-light slate blue `#F5F8FF`. Primary cards, tables, panels, and modals rest on pure white `#FFFFFF` surfaces.
- **Primary Brand Interactive (`#1D4ED8`)**: Used for primary action buttons, selected navigation states, table sort indicators, active step headers, and critical links.
- **Dark Navy Structural (`#1E3A8A`)**: Applied to high-level headers, page titles, sidebar brand blocks, and prominent KPIs.
- **Soft Blue Tint (`#DBEAFE`)**: Used for icon backgrounds, subtle badges, table selection highlights, and secondary informative callouts.
- **Neutrals & Borders**:
  - Primary text: Slate-900 `#0F172A` (maximum legibility for numbers and labels).
  - Secondary/Muted text: Slate-600 `#475569`.
  - Dividers and structural card outlines: Slate-200 `#E2E8F0`.
  - Input and toggle boundaries: Slate-300 `#CBD5E1`.
- **Status & Telemetry Badges (Operational States)**:
  - *Disponible / Devuelto*: Soft green background `#DCFCE7`, text `#15803D`.
  - *Alquilado / Activo*: Warm amber background `#FEF3C7`, text `#B45309`.
  - *Con mora / Mantenimiento*: Soft red background `#FEE2E2`, text `#B91C1C`.

## Typography
Inter is used uniformly across all interface tiers to preserve geometric neutrality and high tabular data clarity.

- **Currency Rules**: Colombian Peso amounts must always format using standard thousand separators with a space following the symbol (e.g., `$ 350.000` or `$ 1.250.000 COP`). Never truncate values to ambiguous decimals unless specifically graphing metrics.
- **Numbers & Metrics**: Tabular figures (`tnum`) should be enabled for tabular records, vehicle plate values (`ABC-123`), odometer readings, and financial summaries to guarantee vertical alignment.
- **Casing**: UI labels and micro-copy are presented in sentence case, except for vehicle license plates and document tags (e.g., `C.C.`, `NIT`, `PLACA`), which remain uppercase.

## Layout & Spacing
The layout model is anchored to a primary desktop canvas optimized for a minimum viewport width of 1440px.

- **Grid Architecture**: A fixed-width 12-column grid system is deployed within the main viewport workspace, operating alongside a fixed 260px collapsible operational navigation sidebar. The content workspace uses 24px (`1.5rem`) gutters and a minimum horizontal margin of 32px (`2rem`).
- **Vertical Rhythm**: Form segments, summary statistics cards, and grid sections follow an 8px grid cadence (`space-sm` for compact internal paddings, `space-md` for standard component groupings, and `space-xl` for layout division).
- **Density Controls**: Data tables support dense views (row heights of 44px) and comfortable views (row heights of 56px) to accommodate dense vehicle inventory audits versus customer onboarding workflows.

## Elevation & Depth
Depth is produced through subtle surface elevation paired with sharp container outlines rather than aggressive drop shadows.

- **Level 0 (Base Canvas)**: Background `#F5F8FF`, completely flat without shadow.
- **Level 1 (Cards, Data Tables, Form Containers)**: Surface `#FFFFFF`, 1px solid border `#E2E8F0`, ambient shadow `0 1px 3px 0 rgba(15, 23, 42, 0.05), 0 1px 2px -1px rgba(15, 23, 42, 0.03)`.
- **Level 2 (Dropdowns, Popovers, Floating Action Menus)**: Surface `#FFFFFF`, 1px solid border `#CBD5E1`, shadow `0 4px 6px -1px rgba(15, 23, 42, 0.08), 0 2px 4px -2px rgba(15, 23, 42, 0.04)`.
- **Level 3 (Modals, Vehicle Inspection Drawers, Overlays)**: Surface `#FFFFFF`, 1px solid border `#E2E8F0`, deep shadow `0 20px 25px -5px rgba(15, 23, 42, 0.12), 0 8px 10px -6px rgba(15, 23, 42, 0.06)`, flanked by backdrop dimming `#0F172A` at 40% opacity.

## Shapes
The design system applies a disciplined roundedness scale between 8px and 12px to humanize utility-heavy enterprise flows while retaining an architectural, trustworthy presence.

- **Micro elements (Inputs, Buttons, Badges, Search Bars)**: Standardized to 8px (`0.5rem`) corner radiuses for precise alignment against grid lines.
- **Macro containers (Data Cards, Modal Dialogs, Vehicle Detail Drawers)**: Scaled to 12px (`0.75rem` / `rounded-lg`) to distinctively enclose modular operational units.
- **Indicator tags & Pills**: Rounded to full capsule geometry (`9999px`) exclusively for small textual flags such as vehicle transmission (`Automático` / `Mecánico`) or small counters.

## Components

### Buttons
- **Primary**: Background `#1D4ED8`, text `#FFFFFF`, 8px radius. Height 40px, font weight 500. Hover: `#1E3A8A`. Active: `#172554`.
- **Secondary / Outline**: Background `#FFFFFF`, border 1px solid `#CBD5E1`, text `#0F172A`. Hover: Background `#F5F8FF`, border `#1D4ED8`, text `#1D4ED8`.
- **Ghost**: Transparent background, text `#475569`. Hover: Background `#DBEAFE` at 50% opacity, text `#1D4ED8`.

### Input Fields & Search Bars
- Standard height 40px, surface `#FFFFFF`, 1px border `#CBD5E1`, 8px radius. Text `#0F172A`, placeholder `#94A3B8`.
- Focus state: 1px border `#1D4ED8` with a 3px outer glow ring of `rgba(29, 78, 216, 0.15)`.
- Global search includes integrated left icon (search glass) and an inline shortcut indicator (`Ctrl + K` or `/`).

### Badges & Status Chips
- Height 24px, 6px or 8px radius, horizontal padding 8px, font size 12px, font weight 600.
- **Disponible / Devuelto**: Background `#DCFCE7`, text `#15803D`.
- **Alquilado / Activo**: Background `#FEF3C7`, text `#B45309`.
- **Con mora**: Background `#FEE2E2`, text `#B91C1C`.
- **Fleet Category Badges (SUV, Sedán, Camioneta)**: Background `#DBEAFE`, text `#1E3A8A`.

### Cards & Metrics (KPI Panels)
- White container `#FFFFFF`, 12px radius, 1px border `#E2E8F0`, padding 20px (`1.25rem`).
- Metric headers display a title in `label-md` (`#475569`), accompanied by a soft blue rounded icon badge (36x36px `#DBEAFE` with `#1D4ED8` icon). Metric numbers utilize bold `display-lg` sizing with formatted COP values.

### Tables & Inventory Data Grids
- Headers: Background `#F8FAFC`, bottom border 1px solid `#CBD5E1`, text `#475569` in `label-md` uppercase, height 44px.
- Rows: Alternating hover state `#F8FAFC`, bottom border 1px solid `#E2E8F0`. Action column features inline secondary icon buttons (`edit`, `receipt`, `calendar`).

### Selection Controls (Checkboxes & Radios)
- Checkboxes: 18x18px, 4px border radius, border `#CBD5E1`. Checked state: background `#1D4ED8`, white checkmark icon.
- Radios: 18x18px circle, border `#CBD5E1`. Checked state: outer ring `#1D4ED8` with a solid 6px internal blue core.