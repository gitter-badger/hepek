package ba.sake.hepek.bootstrap3

package component

import scalatags.Text.all._

object BootstrapNavbarComponent extends BootstrapNavbarComponent {

  sealed trait Position {
    def classes: String
    def stylesInline: String
  }

  object Position {
    case object FixedTop extends Position {
      def classes      = "navbar-fixed-top"
      def stylesInline = "body { padding-top: 55px; }"
    }
    case object FixedBottom extends Position {
      def classes      = "navbar-fixed-bottom"
      def stylesInline = "body { padding-bottom: 55px; }"
    }
  }

  sealed trait Style { def classes: String }

  object Style {
    case object Default extends Style { def classes = "navbar-default" }
    case object Inverse extends Style { def classes = "navbar-inverse" }
  }

  sealed trait Alignment { def classes: String }

  object Alignment {
    case object Left  extends Alignment { def classes = "navbar-left"  }
    case object Right extends Alignment { def classes = "navbar-right" }
  }

}

trait BootstrapNavbarComponent extends BootstrapDependencies {
  import BootstrapNavbarComponent._

  private val nav   = tag("nav")
  private val bsBtn = tag("button")(tpe := "button", cls := "btn ")

  def bootstrapNavbarStyle: Style = Style.Default

  def bootstrapNavbarPosition: Position = Position.FixedTop

  abstract override def stylesInline = {
    // see https://getbootstrap.com/docs/3.3/components/#navbar-fixed-bottom
    val padding = bootstrapNavbarPosition.stylesInline
    super.stylesInline ++ List(padding)
  }

  def navbar(content: Frag*): Frag = {
    val positionClass = bootstrapNavbarPosition.classes
    val styleClass    = bootstrapNavbarStyle.classes
    nav(cls := s"navbar $styleClass $positionClass")(
      div(cls := "container")(content)
    )
  }

  def navbarHeader() =
    div(cls := "navbar-header")

  def navbarContent(align: Alignment = Alignment.Right) =
    ul(cls := s"nav navbar-nav ${align.classes}")

  def navbarBrand(homeURL: String) =
    a(cls := "navbar-brand", href := homeURL)

  def navbarCollapse(navCollapseId: String = "main-navbar") =
    div(cls := "collapse navbar-collapse", id := navCollapseId)

  def navbarCollapseToggleBtn(navCollapseId: String = "main-navbar") =
    bsBtn(cls := "navbar-toggle collapsed ",
          data.toggle := "collapse",
          data.target := s"#$navCollapseId")(
      span(cls := "sr-only")("Toggle navigation"),
      span(cls := "icon-bar"),
      span(cls := "icon-bar"),
      span(cls := "icon-bar")
    )

}
