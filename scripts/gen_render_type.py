import os
import json

BLOCK_MODELS_DIR = "../src/main/resources/assets/goety/models/block"
BLOCK_REGISTRY_PREFIX = "ModBlocks."

RENDER_TYPE_MAP = {
    "minecraft:cutout": "RenderType.cutout()",
    "minecraft:cutout_mipped": "RenderType.cutoutMipped()",
    "minecraft:translucent": "RenderType.translucent()",
    "cutout": "RenderType.cutout()",
    "cutout_mipped": "RenderType.cutoutMipped()",
    "translucent": "RenderType.translucent()"
}

def generate():
    if not os.path.exists(BLOCK_MODELS_DIR):
        return

    count = 0
    for file_name in sorted(os.listdir(BLOCK_MODELS_DIR)):
        if file_name.endswith('.json'):
            file_path = os.path.join(BLOCK_MODELS_DIR, file_name)

            try:
                with open(file_path, 'r', encoding='utf-8') as f:
                    data = json.load(f)

                    if "render_type" in data:
                        forge_render_type = data["render_type"]

                        block_name = file_name.replace('.json', '').upper()

                        fabric_render_layer = RENDER_TYPE_MAP.get(forge_render_type, f"// 未知类型: {forge_render_type}")

                        print(f"BlockRenderLayerMap.INSTANCE.putBlock({BLOCK_REGISTRY_PREFIX}{block_name}, {fabric_render_layer});")
                        count += 1
            except Exception as e:
                print(f"{str(e)}")


if __name__ == "__main__":
    generate()
