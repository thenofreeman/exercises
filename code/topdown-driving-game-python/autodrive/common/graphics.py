import pygame

pygame.font.init()
font = pygame.font.SysFont('Comic Sans MS', 30)

def load_image(img_src, dim):
    image = pygame.image.load(img_src)

    return pygame.transform.scale(image, dim)

def scale_image(img, factor):
    size = round(img.get_width() * factor), round(img.get_height() * factor)

    return pygame.transform.scale(img, size)

def blit_image(surface, image, topleft, angle=0):
    rotated_image = pygame.transform.rotate(image, angle)
    new_rect = rotated_image.get_rect(center=image.get_rect(topleft=topleft).center)

    surface.blit(rotated_image, new_rect.topleft)