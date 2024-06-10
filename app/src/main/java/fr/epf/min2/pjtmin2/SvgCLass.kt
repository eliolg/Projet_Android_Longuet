package fr.epf.min2.pjtmin2

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.load.Options
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder
import com.caverock.androidsvg.SVG
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.load.ResourceDecoder
import com.caverock.androidsvg.SVGParseException
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import java.io.InputStream


class SvgSoftwareLayerSetter : ImageViewTarget<PictureDrawable> {
    constructor(view: ImageView) : super(view)

    override fun setResource(resource: PictureDrawable?) {
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        view.setImageDrawable(resource)
    }

    override fun setDrawable(drawable: Drawable?) {
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        view.setImageDrawable(drawable)
    }
}


class SvgDecoder : ResourceDecoder<InputStream, SVG> {

    override fun handles(source: InputStream, options: Options): Boolean = true

    override fun decode(source: InputStream, width: Int, height: Int, options: Options): Resource<SVG>? {
        try {
            val svg = SVG.getFromInputStream(source)
            return SimpleResource(svg)
        } catch (ex: SVGParseException) {
            throw ex
        }
    }
}


@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.register(SVG::class.java, PictureDrawable::class.java, SvgDrawableTranscoder())
            .append(InputStream::class.java, SVG::class.java, SvgDecoder())
    }
}




class SvgDrawableTranscoder : ResourceTranscoder<SVG, PictureDrawable> {
    override fun transcode(toTranscode: Resource<SVG>, options: Options): Resource<PictureDrawable> {
        val svg = toTranscode.get()
        val picture = svg.renderToPicture()
        val drawable = PictureDrawable(picture)
        return SimpleResource(drawable)
    }
}